package com.intuit.services.impl;

import com.intuit.dao.*;
import com.intuit.dao.entities.Callback;
import com.intuit.dao.entities.TimeSlot;
import com.intuit.dao.entities.User;
import com.intuit.enums.CallbackStatus;
import com.intuit.exceptions.ValidationException;
import com.intuit.models.requests.CallbackEntryCreateReq;
import com.intuit.services.CallbackEntryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import static com.intuit.utils.Constants.REP_MAX_CALL_PER_HOUR;
import static com.intuit.utils.Constants.TIME_SLOT_DURATION;

/**
 * Created by Sunil on 9/1/19.
 */
@Service
public class CallbackEntryServiceImpl implements CallbackEntryService {

    @Autowired
    CallbackDao callbackDao;

    @Autowired
    TimeSlotDao timeSlotDao;

    @Autowired
    RepDao repDao;

    @Autowired
    MailService mailService;

    @Autowired
    UserDao userDao;

    @Override
    public String add(CallbackEntryCreateReq callbackReq) throws ValidationException {
        validateCreateReq(callbackReq);
        Callback instance = Callback.getInstance(callbackReq);
        String callbackId = callbackDao.save(instance);
        confirmMail(callbackId);
        return callbackId;
    }

    /**
     * Validation on the callback entry request
     * user_id and callback objects cannot be empty
     * @param callback
     * @throws ValidationException
     */
    public void validateCreateReq(CallbackEntryCreateReq callback) throws ValidationException {
        List<String> errors = new ArrayList<>();
        if(StringUtils.isEmpty(callback.getUserId())) {
            errors.add("userId");
        }

        if(StringUtils.isEmpty(callback.getTimeSlotId())) {
            errors.add("time slot");
        }

        if(callback.getStartTime() == null || callback.getStartTime() == null) {
            errors.add("date slot");
        }

        if(errors.size() > 0) {
            throw new ValidationException("Missing: " + StringUtils.join(errors, ","));
        }
    }

    public void confirmMail(String callbackId) throws ValidationException {
        Callback callback = callbackDao.findOne(callbackId);
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        TimeSlot timeSlot = timeSlotDao.getTime(callback.getTimeSlotId());

        Long startTimeSlot = generateTimeStamp(cal, timeSlot.getStartTime());
        Long endTimeSlot = generateTimeStamp(cal, timeSlot.getEndTime());

        System.out.println("scheduledStartTime: " + callback.getStartTime() + " scheduledEndTime: " + callback.getEndTime());
        System.out.println("startTimeSlot: " + startTimeSlot + " endTimeSlot: " + endTimeSlot);

        long allotedSlotsCount = callbackDao.countDocumentsInTimeSlot(startTimeSlot, endTimeSlot);
        long maxCallsForSlot = repDao.count() * REP_MAX_CALL_PER_HOUR * TIME_SLOT_DURATION;


        User user = userDao.findOne(callback.getUserId());
        Callback updateDoc = new Callback();
        if(allotedSlotsCount < maxCallsForSlot) {
            mailService.sendConfirmationMail(user, callback);
            updateDoc.setStatus(CallbackStatus.CONFIRMATION_MAIL);
        } else {
            mailService.sendWaitingNotification(user, callback);
            updateDoc.setStatus(CallbackStatus.WAITING);
        }
        update(callbackId, updateDoc);
    }

    public void update(String callbackId, Callback callback) throws ValidationException {
        callbackDao.updateOne(callbackId, callback);
    }

    public Long generateTimeStamp(Calendar cal, Time time) {
        Calendar timeCal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        timeCal.setTime(time);

        cal.set(Calendar.HOUR_OF_DAY, timeCal.get(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));
        cal.set(Calendar.SECOND, timeCal.get(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }
}

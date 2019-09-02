package com.intuit.services.impl;

import com.intuit.dao.*;
import com.intuit.dao.entities.Callback;
import com.intuit.dao.entities.TimeSlot;
import com.intuit.dao.entities.User;
import com.intuit.enums.CallbackStatus;
import com.intuit.exceptions.ValidationException;
import com.intuit.models.ScheduleTimeSlot;
import com.intuit.services.CallbackEntryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

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
    public String add(Callback callbackReq) throws ValidationException {
        validateCreateReq(callbackReq);
        Callback instance = Callback.getInstance(callbackReq);
        String callbackId = callbackDao.save(instance);
        confirmMail(callbackId);
        return callbackId;
    }

    @Override
    public void reschedule(String callbackId, Callback callback) throws ValidationException {
        if(callback.getStartTime() == null || callback.getStartTime() == null) {
            throw new ValidationException("Missing time slots");
        }
        callback.setStatus(CallbackStatus.NOT_STARTED);
        callbackDao.updateOne(callbackId, callback);
        confirmMail(callbackId);
    }

    @Override
    public void cancel(String callbackId) throws ValidationException {
        Callback callback = callbackDao.findOne(callbackId);
        callbackDao.deleteOne(callbackId);

        // Check for waiting list only if confirmed customer cancels
        if(callback.getStatus() != CallbackStatus.CONFIRMATION_MAIL) {
            return;
        }

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        TimeSlot timeSlot = timeSlotDao.getTime(callback.getTimeSlotId());
        Long startTimeSlot = generateTimeStamp(cal, timeSlot.getStartTime());
        Long endTimeSlot = generateTimeStamp(cal, timeSlot.getEndTime());

        // Check for waiting customer and send mail
        Callback waitingCustomerCallback = callbackDao.getOneWaitingCustomer(startTimeSlot, endTimeSlot, CallbackStatus.WAITING);
        if(waitingCustomerCallback != null) {
            confirmSlotForWaitingCustomer(waitingCustomerCallback);
        }
    }

    /**
     * Confirm call for waiting customer
     * @param callback
     * @throws ValidationException
     */
    public void confirmSlotForWaitingCustomer(Callback callback) throws ValidationException {
        User user = userDao.findOne(callback.getUserId());
        Callback updateDoc = new Callback();
        mailService.sendConfirmationMail(user, callback);
        updateDoc.setStatus(CallbackStatus.CONFIRMATION_MAIL);
        update(callback.getId(), updateDoc);
    }

    @Override
    public void confirmMail(String callbackId) throws ValidationException {
        Callback callback = callbackDao.findOne(callbackId);
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        TimeSlot timeSlot = timeSlotDao.getTime(callback.getTimeSlotId());

        Long startTimeSlot = generateTimeStamp(cal, timeSlot.getStartTime());
        Long endTimeSlot = generateTimeStamp(cal, timeSlot.getEndTime());

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

    @Override
    public void update(String callbackId, Callback callback) throws ValidationException {
        callbackDao.updateOne(callbackId, callback);
    }

    /**
     * Validation on the callback entry request
     * user_id and callback objects cannot be empty
     * @param callback
     * @throws ValidationException
     */
    private void validateCreateReq(Callback callback) throws ValidationException {
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

    @Override
    public void sendNotificationMailForNextSlot(ScheduleTimeSlot scheduleTimeSlot) {
        List<Callback> callbackList = callbackDao.userIdsForNotification(scheduleTimeSlot);
        if(callbackList.size() > 0) {
            List<String> userIds = callbackList.stream().map(Callback::getUserId).collect(Collectors.toList());
            List<String> callbackIds = callbackList.stream().map(Callback::getId).collect(Collectors.toList());

            callbackDao.updateMultipleCallbackStatus(callbackIds, CallbackStatus.NOTIFICATION_MAIL);
            List<String> emailIds = userDao.findEmailIdsForUsers(userIds);
            mailService.sendNotificationMail(emailIds, scheduleTimeSlot);
        }
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

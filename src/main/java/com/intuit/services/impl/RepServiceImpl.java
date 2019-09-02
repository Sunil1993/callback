package com.intuit.services.impl;

import com.intuit.dao.RepDao;
import com.intuit.dao.UserDao;
import com.intuit.dao.entities.Callback;
import com.intuit.dao.entities.Rep;
import com.intuit.dao.entities.User;
import com.intuit.enums.CallbackStatus;
import com.intuit.exceptions.ValidationException;
import com.intuit.models.TimeSlotInTimestamp;
import com.intuit.models.responses.RepAssignCallResponse;
import com.intuit.services.CallbackEntryService;
import com.intuit.services.RepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

/**
 * Created by Sunil on 9/2/19.
 */
@Service
public class RepServiceImpl implements RepService {

    @Autowired
    RepDao repDao;

    @Autowired
    CallbackEntryService callbackEntryService;

    @Autowired
    UserDao userDao;

    @Override
    public String createEntry(Rep rep) {
        return repDao.save(rep);
    }

    @Override
    public RepAssignCallResponse assignCall(String repId, TimeSlotInTimestamp timeSlot) throws ValidationException {
        Callback callback = callbackEntryService.getCustomerInTimeSlot(timeSlot, repId);
        if(callback == null) {
            throw new NoSuchElementException("No customer found for the given time range");
        }

        User user = userDao.findOne(callback.getUserId());
        return new RepAssignCallResponse(user, callback);
    }

    @Override
    public void completeCall(String repId, String callbackId) throws ValidationException {
        Callback callbackUpdateObj = new Callback();
        callbackUpdateObj.setStatus(CallbackStatus.DONE);
        callbackEntryService.update(callbackId, callbackUpdateObj);
    }
}

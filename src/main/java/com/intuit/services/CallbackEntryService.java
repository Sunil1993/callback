package com.intuit.services;

import com.intuit.dao.entities.Callback;
import com.intuit.exceptions.ValidationException;
import com.intuit.models.ScheduleTimeSlot;
import com.intuit.models.requests.CallbackEntryCreateReq;

/**
 * Created by Sunil on 9/1/19.
 */
public interface CallbackEntryService {
    String add(Callback Callback) throws ValidationException;

    void cancel(String callbackId) throws ValidationException;

    void confirmMail(String callbackId) throws ValidationException;

    void update(String callbackId, Callback callback) throws ValidationException;

    void sendNotificationMailForNextSlot(ScheduleTimeSlot scheduleTimeSlot);

    void reschedule(String callbackId, Callback callback) throws ValidationException;
}

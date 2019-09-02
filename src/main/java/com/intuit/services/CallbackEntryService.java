package com.intuit.services;

import com.intuit.dao.entities.Callback;
import com.intuit.exceptions.ValidationException;
import com.intuit.models.TimeSlotInTimestamp;

/**
 * Created by Sunil on 9/1/19.
 */
public interface CallbackEntryService {
    String add(Callback Callback) throws ValidationException;

    Callback getCustomerInTimeSlot(TimeSlotInTimestamp timeSlot, String repId);

    void cancel(String callbackId) throws ValidationException;

    void confirmMail(String callbackId) throws ValidationException;

    void update(String callbackId, Callback callback) throws ValidationException;

    void sendNotificationMailForNextSlot(TimeSlotInTimestamp scheduleTimeSlot);

    void reschedule(String callbackId, Callback callback) throws ValidationException;
}

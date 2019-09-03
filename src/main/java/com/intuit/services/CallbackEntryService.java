package com.intuit.services;

import com.intuit.dao.entities.Callback;
import com.intuit.exceptions.PersistentException;
import com.intuit.exceptions.ValidationException;
import com.intuit.models.TimeSlotInTimestamp;

/**
 * Created by Sunil on 9/1/19.
 */
public interface CallbackEntryService {
    String add(Callback Callback) throws ValidationException, PersistentException;

    Callback getCustomerInTimeSlot(TimeSlotInTimestamp timeSlot, String repId) throws PersistentException;

    void cancel(String callbackId) throws ValidationException, PersistentException;

    void confirmMail(String callbackId) throws ValidationException, PersistentException;

    void update(String callbackId, Callback callback) throws ValidationException, PersistentException;

    void sendNotificationMailForNextSlot(TimeSlotInTimestamp scheduleTimeSlot) throws PersistentException;

    void reschedule(String callbackId, Callback callback) throws ValidationException, PersistentException;
}

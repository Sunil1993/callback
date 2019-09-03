package com.intuit.dao;

import com.intuit.dao.entities.Callback;
import com.intuit.enums.CallbackStatus;
import com.intuit.exceptions.PersistentException;
import com.intuit.exceptions.ValidationException;
import com.intuit.models.TimeSlotInTimestamp;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Sunil on 9/1/19.
 */
@Repository
public interface CallbackDao {
    String save(Callback callback) throws PersistentException;

    long countDocumentsInTimeSlot(Long startTime, Long endTime) throws PersistentException;

    Callback findOne(String id) throws ValidationException, PersistentException;

    void updateOne(String id, Callback callback) throws ValidationException, PersistentException;

    List<Callback> userIdsForNotification(TimeSlotInTimestamp scheduleTimeSlot) throws PersistentException;

    long countDocumentsInTimeSlotWithStatus(Long startTime, Long endTime, CallbackStatus status);

    void updateMultipleCallbackStatus(List<String> callbackIds, CallbackStatus status) throws PersistentException;

    Callback getOneWaitingCustomer(Long startTime, Long endTime, CallbackStatus status) throws PersistentException;

    void deleteOne(String callbackId) throws ValidationException, PersistentException;

    Callback assignRep(TimeSlotInTimestamp timeSlot, String repId) throws PersistentException;
}

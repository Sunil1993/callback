package com.intuit.dao;

import com.intuit.dao.entities.Callback;
import com.intuit.enums.CallbackStatus;
import com.intuit.exceptions.ValidationException;
import com.intuit.models.ScheduleTimeSlot;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Sunil on 9/1/19.
 */
@Repository
public interface CallbackDao {
    String save(Callback callback);

    long countDocumentsInTimeSlot(Long startTime, Long endTime);

    Callback findOne(String id) throws ValidationException;

    void updateOne(String id, Callback callback) throws ValidationException;

    List<Callback> userIdsForNotification(ScheduleTimeSlot scheduleTimeSlot);

    long countDocumentsInTimeSlotWithStatus(Long startTime, Long endTime, CallbackStatus status);

    void updateMultipleCallbackStatus(List<String> callbackIds, CallbackStatus status);

    Callback getOneWaitingCustomer(Long startTime, Long endTime, CallbackStatus status);

    void deleteOne(String callbackId) throws ValidationException;
}

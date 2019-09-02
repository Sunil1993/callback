package com.intuit.dao;

import com.intuit.dao.entities.TimeSlot;
import com.intuit.exceptions.ValidationException;

/**
 * Created by Sunil on 9/1/19.
 */
public interface TimeSlotDao {
    void upsert(TimeSlot timeSlot);

    TimeSlot getTime(String id) throws ValidationException;
}

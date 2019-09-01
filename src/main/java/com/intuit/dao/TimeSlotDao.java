package com.intuit.dao;

import com.intuit.dao.entities.TimeSlot;

/**
 * Created by Sunil on 9/1/19.
 */
public interface TimeSlotDao {
    void upsert(TimeSlot timeSlot);
}

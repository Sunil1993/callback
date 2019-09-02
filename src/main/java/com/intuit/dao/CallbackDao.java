package com.intuit.dao;

import com.intuit.dao.entities.Callback;
import org.springframework.stereotype.Repository;

/**
 * Created by Sunil on 9/1/19.
 */
@Repository
public interface CallbackDao {
    String save(Callback callback);

    long countDocumentsInTimeSlot(Long startTime, Long endTime);
}

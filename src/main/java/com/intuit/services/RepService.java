package com.intuit.services;

import com.intuit.dao.entities.Rep;
import com.intuit.exceptions.ValidationException;
import com.intuit.models.TimeSlotInTimestamp;
import com.intuit.models.responses.RepAssignCallResponse;

/**
 * Created by Sunil on 9/1/19.
 */
public interface RepService {
    String createEntry(Rep rep);

    RepAssignCallResponse assignCall(String repId, TimeSlotInTimestamp timeSlot) throws ValidationException;

    void completeCall(String repId, String callbackId) throws ValidationException;
}

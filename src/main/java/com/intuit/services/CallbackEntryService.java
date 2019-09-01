package com.intuit.services;

import com.intuit.dao.entities.Callback;
import com.intuit.exceptions.ValidationException;
import com.intuit.models.requests.CallbackEntryCreateReq;

/**
 * Created by Sunil on 9/1/19.
 */
public interface CallbackEntryService {
    String add(CallbackEntryCreateReq Callback) throws ValidationException;
}

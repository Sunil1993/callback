package com.intuit.services;

import com.intuit.exceptions.ValidationException;
import com.intuit.models.requests.CallbackEntryCreateReq;

/**
 * Created by Sunil on 9/1/19.
 */
public interface CallbackEntryService {
    int add(CallbackEntryCreateReq callbackEntryCreateReq) throws ValidationException;
}

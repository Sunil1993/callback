package com.intuit.services;

import com.intuit.dao.entities.Callback;
import com.intuit.exceptions.ValidationException;

/**
 * Created by Sunil on 9/1/19.
 */
public interface CallbackEntryService {
    String add(Callback Callback) throws ValidationException;
}

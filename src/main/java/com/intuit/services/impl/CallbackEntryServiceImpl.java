package com.intuit.services.impl;

import com.intuit.dao.CallbackEntryRepository;
import com.intuit.dao.UserRepository;
import com.intuit.dao.entities.CallbackEntry;
import com.intuit.dao.entities.User;
import com.intuit.exceptions.ValidationException;
import com.intuit.models.requests.CallbackEntryCreateReq;
import com.intuit.services.CallbackEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Sunil on 9/1/19.
 */
@Service
public class CallbackEntryServiceImpl implements CallbackEntryService {

    @Autowired
    CallbackEntryRepository callbackEntryRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public int add(CallbackEntryCreateReq callbackEntryCreateReq) throws ValidationException {
        validateCreateReq(callbackEntryCreateReq);
        CallbackEntry callbackEntryObj = callbackEntryCreateReq.getCallbackEntry();
        User user = userRepository.findOne(callbackEntryCreateReq.getUserId());
        if(user == null) {
            throw new ValidationException("Not a valid customer");
        }

        callbackEntryObj.setUser(user);
        CallbackEntry entry = callbackEntryRepository.save(callbackEntryObj);
        return entry.getId();
    }

    /**
     * Validation on the callback entry request
     * user_id and callback objects cannot be empty
     * @param callbackEntryCreateReq
     * @throws ValidationException
     */
    public void validateCreateReq(CallbackEntryCreateReq callbackEntryCreateReq) throws ValidationException {
        if(callbackEntryCreateReq.getUserId() == null) {
            throw new ValidationException("Please provide valid customer id");
        }

        if(callbackEntryCreateReq.getCallbackEntry() == null) {
            throw new ValidationException("Please provide valid parameters");
        }
    }
}

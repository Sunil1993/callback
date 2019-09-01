package com.intuit.services.impl;

import com.intuit.dao.CallbackDao;
import com.intuit.dao.UserDao;
import com.intuit.dao.entities.Callback;
import com.intuit.exceptions.ValidationException;
import com.intuit.services.CallbackEntryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Sunil on 9/1/19.
 */
@Service
public class CallbackEntryServiceImpl implements CallbackEntryService {

    @Autowired
    CallbackDao callbackDao;

    @Autowired
    UserDao userRepository;

    @Override
    public String add(Callback callback) throws ValidationException {

        validateCreateReq(callback);
        return callbackDao.save(callback);
    }

    /**
     * Validation on the callback entry request
     * user_id and callback objects cannot be empty
     * @param callback
     * @throws ValidationException
     */
    public void validateCreateReq(Callback callback) throws ValidationException {
        if(StringUtils.isEmpty(callback.getUserId())) {
            throw new ValidationException("Missing customer id");
        }

        if(StringUtils.isEmpty(callback.getTimeSlotId())) {
            throw new ValidationException("Missing time slot");
        }
    }
}

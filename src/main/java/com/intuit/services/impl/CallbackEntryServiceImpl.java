package com.intuit.services.impl;

import com.intuit.dao.CallbackDao;
import com.intuit.dao.UserDao;
import com.intuit.dao.entities.Callback;
import com.intuit.exceptions.ValidationException;
import com.intuit.models.requests.CallbackEntryCreateReq;
import com.intuit.services.CallbackEntryService;
import com.intuit.utils.DateUtils;
import com.intuit.utils.ObjectMapperUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Sunil on 9/1/19.
 */
@Service
public class CallbackEntryServiceImpl implements CallbackEntryService {

    @Autowired
    CallbackDao callbackDao;

    @Override
    public String add(CallbackEntryCreateReq callbackReq) throws ValidationException {
        validateCreateReq(callbackReq);
        Callback instance = Callback.getInstance(callbackReq);
        instance.setSlotDate(DateUtils.removeTime(callbackReq.getDate()));
        return callbackDao.save(instance);
    }

    /**
     * Validation on the callback entry request
     * user_id and callback objects cannot be empty
     * @param callback
     * @throws ValidationException
     */
    public void validateCreateReq(CallbackEntryCreateReq callback) throws ValidationException {
        List<String> errors = new ArrayList<>();
        if(StringUtils.isEmpty(callback.getUserId())) {
            errors.add("userId");
        }

        if(StringUtils.isEmpty(callback.getTimeSlotId())) {
            errors.add("time slot");
        }

        if(callback.getDate() == null) {
            errors.add("date slot");
        }

        if(errors.size() > 0) {
            throw new ValidationException("Missing: " + StringUtils.join(errors, ","));
        }
    }
}

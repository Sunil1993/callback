package com.intuit.services;

import com.intuit.dao.entities.User;
import com.intuit.exceptions.ValidationException;
import com.intuit.models.requests.UserCreateReq;

/**
 * Created by Sunil on 9/1/19.
 */
public interface UserService {
    Integer createUser(UserCreateReq userCreateReq);
}

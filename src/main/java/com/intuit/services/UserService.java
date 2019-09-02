package com.intuit.services;

import com.intuit.models.requests.UserCreateReq;

/**
 * Created by Sunil on 9/1/19.
 */
public interface UserService {
    String createUser(UserCreateReq userCreateReq);
}

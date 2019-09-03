package com.intuit.services.impl;

import com.intuit.dao.UserDao;
import com.intuit.dao.entities.User;
import com.intuit.exceptions.PersistentException;
import com.intuit.models.requests.UserCreateReq;
import com.intuit.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by Sunil on 9/1/19.
 */
@Service
@Log4j2
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public String createUser(UserCreateReq userCreateReq) throws PersistentException {
        User userObj = User.getInstance(userCreateReq);
        return userDao.save(userObj);
    }
}

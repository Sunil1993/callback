package com.intuit.dao;

import com.intuit.dao.entities.User;
import com.intuit.exceptions.ValidationException;

/**
 * Created by Sunil on 9/1/19.
 */
public interface UserDao {
    String save(User user);
    User findOne(String id) throws ValidationException;
}

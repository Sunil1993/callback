package com.intuit.dao;

import com.intuit.dao.entities.User;
import com.intuit.exceptions.PersistentException;
import com.intuit.exceptions.ValidationException;

import java.util.List;

/**
 * Created by Sunil on 9/1/19.
 */
public interface UserDao {
    String save(User user) throws PersistentException;

    User findOne(String id) throws ValidationException, PersistentException;

    List<String> findEmailIdsForUsers(List<String> userIds) throws PersistentException;
}

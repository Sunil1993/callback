package com.intuit.dao;

import com.intuit.dao.entities.Rep;
import com.intuit.dao.entities.User;
import com.intuit.exceptions.PersistentException;

/**
 * Created by Sunil on 9/2/19.
 */
public interface RepDao {
    String save(Rep rep) throws PersistentException;

    long count() throws PersistentException;
}

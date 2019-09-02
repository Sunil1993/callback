package com.intuit.dao;

import com.intuit.dao.entities.Rep;
import com.intuit.dao.entities.User;

/**
 * Created by Sunil on 9/2/19.
 */
public interface RepDao {
    String save(Rep rep);

    long count();
}

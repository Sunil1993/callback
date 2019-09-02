package com.intuit.services.impl;

import com.intuit.dao.RepDao;
import com.intuit.dao.entities.Rep;
import com.intuit.services.RepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Sunil on 9/2/19.
 */
@Service
public class RepServiceImpl implements RepService {

    @Autowired
    RepDao repDao;

    @Override
    public String createEntry(Rep rep) {
        return repDao.save(rep);
    }
}

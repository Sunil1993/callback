package com.intuit.services.impl;

import com.intuit.dao.RepRepository;
import com.intuit.dao.UserRepository;
import com.intuit.dao.entities.Rep;
import com.intuit.dao.entities.User;
import com.intuit.models.requests.UserCreateReq;
import com.intuit.services.RepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Sunil on 9/1/19.
 */
@Service
public class RepServiceImpl implements RepService {

    @Autowired
    RepRepository repRepository;

    @Override
    public Integer createEntry(UserCreateReq userCreateReq) {
        Rep repObj = Rep.getInstance(userCreateReq);
        Rep entry = repRepository.save(repObj);
        return entry.getId();
    }
}

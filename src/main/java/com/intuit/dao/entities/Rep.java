package com.intuit.dao.entities;

import com.intuit.enums.UserType;
import com.intuit.models.requests.UserCreateReq;
import lombok.Data;


/**
 * Created by Sunil on 9/1/19.
 */
@Data
public class Rep extends UserAbstract{
    private UserType type = UserType.REP;

    public static Rep getInstance(UserCreateReq userCreateReq) {
        Rep rep = new Rep();
        rep.setEmail(userCreateReq.getEmail());
        rep.setName(userCreateReq.getName());
        rep.setPhoneNo(userCreateReq.getPhoneNo());
        return rep;
    }
}

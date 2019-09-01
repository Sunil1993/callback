package com.intuit.dao.entities;

import com.intuit.enums.UserType;
import com.intuit.models.requests.UserCreateReq;
import lombok.Data;

/**
 * Created by Sunil on 9/1/19.
 */
@Data
public class User extends UserAbstract {
    private UserType type = UserType.CUSTOMER;

    public static User getInstance(UserCreateReq userCreateReq) {
        User user = new User();
        user.setEmail(userCreateReq.getEmail());
        user.setName(userCreateReq.getName());
        user.setPhoneNo(userCreateReq.getPhoneNo());
        return user;
    }
}

package com.intuit.dao.entities;

import lombok.Data;

/**
 * Created by Sunil on 9/1/19.
 */
@Data
public abstract class UserAbstract extends AuditModel {
    String id;
    String email;
    String phoneNo;
    String name;
}

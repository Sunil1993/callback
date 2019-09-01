package com.intuit.dao.entities;

import com.intuit.enums.CallbackStatus;
import lombok.Data;

/**
 * Created by Sunil on 9/1/19.
 */
@Data
public class Callback extends AuditModel {
    String id;
    private CallbackStatus status;
    private User userId;
}

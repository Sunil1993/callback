package com.intuit.dao.entities;

import com.intuit.enums.CallbackStatus;
import com.intuit.utils.ObjectMapperUtil;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

/**
 * Created by Sunil on 9/1/19.
 */
@Data
public class Callback extends AuditModel {
    String id;
    CallbackStatus status;
    String userId;
    String timeSlotId;
    Date slotDate;
    Long startTime;
    Long endTime;

    public static Callback getInstance(Object object){
        return ObjectMapperUtil.getObjectMapper().convertValue(object, Callback.class);
    }
}

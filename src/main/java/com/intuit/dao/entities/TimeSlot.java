package com.intuit.dao.entities;

import lombok.Data;
import java.sql.Time;

/**
 * Created by Sunil on 9/1/19.
 */
@Data
public class TimeSlot extends AuditModel {
    private String id;
    private Time startTime;
    private Time endTime;
}

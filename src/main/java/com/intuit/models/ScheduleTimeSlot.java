package com.intuit.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Sunil on 9/2/19.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleTimeSlot {
    Long startTime;
    Long endTime;
}

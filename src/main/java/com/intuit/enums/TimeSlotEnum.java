package com.intuit.enums;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

/**
 * Created by Sunil on 9/2/19.
 */
@Log4j2
@Getter
public enum TimeSlotEnum {
    TIME_0_TO_3(0, 3),
    TIME_3_TO_6(3, 6),
    TIME_6_TO_9(6, 9),
    TIME_9_TO_12(9, 12),
    TIME_12_TO_15(12, 15),
    TIME_15_TO_18(15,18),
    TIME_18_TO_21(18, 21),
    TIME_21_TO_24(21, 24);

    Integer startTime;
    Integer endTime;

    TimeSlotEnum(Integer startTime, Integer endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
}

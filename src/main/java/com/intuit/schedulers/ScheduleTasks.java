package com.intuit.schedulers;

import com.intuit.dao.entities.Callback;
import com.intuit.dao.entities.TimeSlot;
import com.intuit.enums.TimeSlotEnum;
import com.intuit.models.ScheduleTimeSlot;
import com.intuit.services.CallbackEntryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sunil on 9/2/19.
 */
@Component
@Log4j2
public class ScheduleTasks {

    @Autowired
    CallbackEntryService callbackEntryService;

    /**
     * Run every 3 hours starting from 2
     * 2,5,8,11,14,17,20,23
     */
    @Scheduled(cron = "0 0 2/3 * * *")
    public void scheduleTaskWithCronExpression() {
        long timeMillis = System.currentTimeMillis();
        log.info("Cron Task started :: Execution Time - {}", timeMillis);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeMillis);
        ScheduleTimeSlot scheduleTime = getScheduleTime(cal);

        callbackEntryService.sendNotificationMailForNextSlot(scheduleTime);
    }

//    @Scheduled(cron = "0 * * * * ?")
//    public void scheduleTaskWithCronExpress() {
//        log.info("Basic Cron Task :: Execution Time - {}", System.currentTimeMillis());
//    }

    private ScheduleTimeSlot getScheduleTime(Calendar cal) {
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int startHour = (hour + 1) % 24;
        int endHour = (hour + 4) % 24;

        cal.set(Calendar.HOUR_OF_DAY, startHour);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        ScheduleTimeSlot scheduleTimeSlot = new ScheduleTimeSlot();
        scheduleTimeSlot.setStartTime(cal.getTimeInMillis());
        cal.set(Calendar.HOUR_OF_DAY, endHour);
        scheduleTimeSlot.setEndTime(cal.getTimeInMillis());

        return scheduleTimeSlot;
    }
}

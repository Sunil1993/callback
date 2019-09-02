package com.intuit.dao;

import com.intuit.dao.entities.Callback;
import com.intuit.dao.entities.User;
import com.intuit.models.ScheduleTimeSlot;

import java.util.List;

/**
 * Created by Sunil on 9/2/19.
 */
public interface MailService {
    void sendConfirmationMail(User user, Callback callback);

    void sendNotificationMail(List<String> emails, ScheduleTimeSlot scheduleTimeSlot);

    void sendWaitingNotification(User user, Callback callback);
}

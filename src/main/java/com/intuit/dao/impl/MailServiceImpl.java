package com.intuit.dao.impl;

import com.intuit.dao.MailService;
import com.intuit.dao.entities.Callback;
import com.intuit.dao.entities.User;
import com.intuit.models.ScheduleTimeSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Sunil on 9/2/19.
 */
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    @Async
    public void sendConfirmationMail(User user, Callback callback) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(user.getEmail());

        msg.setSubject("Intuit call confirmation");
        msg.setText("Happy to inform that your call is confirmed " + callback.getStartTime());

        javaMailSender.send(msg);
    }

    @Override
    @Async
    public void sendNotificationMail(List<String> emails, ScheduleTimeSlot slot) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(emails.toArray(new String[emails.size()]));

        msg.setSubject("Intuit call notification");
        msg.setText("Happy to inform that a call has been scheduled between " + slot.getStartTime() + " - " + slot.getEndTime());

        javaMailSender.send(msg);
    }

    @Override
    @Async
    public void sendWaitingNotification(User user, Callback callback) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(user.getEmail());

        msg.setSubject("Intuit call update");
        msg.setText("Sorry the slot is full. You are in waiting list. We will be back with more updates");

        javaMailSender.send(msg);
    }
}

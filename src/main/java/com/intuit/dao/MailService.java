package com.intuit.dao;

import com.intuit.dao.entities.Callback;
import com.intuit.dao.entities.User;

/**
 * Created by Sunil on 9/2/19.
 */
public interface MailService {
    void sendConfirmationMail(User user, Callback callback);

    void sendNotificationMail(User user, Callback callback);

    void sendWaitingNotification(User user, Callback callback);
}

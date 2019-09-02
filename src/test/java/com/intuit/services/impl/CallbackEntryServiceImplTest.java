package com.intuit.services.impl;

import com.intuit.dao.MailService;
import com.intuit.dao.entities.Callback;
import com.intuit.dao.entities.Rep;
import com.intuit.dao.entities.TimeSlot;
import com.intuit.dao.entities.User;
import com.intuit.dao.impl.*;
import com.intuit.enums.CallbackStatus;
import com.intuit.exceptions.ValidationException;
import com.intuit.models.TimeSlotInTimestamp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Time;

import static com.intuit.utils.Constants.HOUR_DURATION;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Created by Sunil on 9/3/19.
 */
@RunWith(MockitoJUnitRunner.class)
public class CallbackEntryServiceImplTest {
    private User user;
    private Rep rep;
    private Callback callback;
    private TimeSlot timeSlot;
    private  TimeSlotInTimestamp timeSlotInTimestamp;
    private String callbackId = "callbackId_123";
    private String timeSlotId = "timeSlotId_123";
    private String userId = "userId_123";

    @Before
    public void setupMock() {
        user = mock(User.class);
        rep = mock(Rep.class);
        callback = mock(Callback.class);
        timeSlotInTimestamp = mock(TimeSlotInTimestamp.class);
        timeSlot = mock(TimeSlot.class);
    }

    @Mock
    CallbackDaoImpl callbackDao;

    @Mock
    UserDaoImpl userDao;

    @Mock
    TimeSlotDaoImpl timeSlotDao;

    @Mock
    RepDaoImpl repDao;

    @Mock
    MailServiceImpl mailService;

    @InjectMocks
    CallbackEntryServiceImpl callbackEntryService;

    @Test
    public void add() throws Exception {
        Callback callbackInstance = getCallbackInstance();
        TimeSlot timeSlotInstance = getTimeSlotInstance();

        when(callbackDao.save(callbackInstance)).thenReturn(callbackId);
        when(callbackDao.findOne(callbackInstance.getId())).thenReturn(callbackInstance);
        when(timeSlotDao.getTime(callbackInstance.getTimeSlotId())).thenReturn(timeSlotInstance);
        when(callbackDao.countDocumentsInTimeSlot(anyLong(), anyLong())).thenReturn(2L);
        when(repDao.count()).thenReturn(30L);
        when(userDao.findOne(anyString())).thenReturn(user);
        doNothing().when(callbackDao).updateOne(anyString(), any(Callback.class));
        doNothing().when(mailService).sendConfirmationMail(any(User.class), any(Callback.class));

        String respCallbackId = callbackEntryService.add(callbackInstance);
        assertEquals(respCallbackId, callbackId);
    }

    @Test(expected = ValidationException.class)
    public void addWithValidation() throws Exception {
        given(callbackEntryService.add(callback)).willThrow(new ValidationException("Missing: userId,time slot,date slot" ));
    }

    @Test
    public void reschedule() throws Exception {
        Callback callbackInstance = getCallbackInstance();
        TimeSlot timeSlotInstance = getTimeSlotInstance();

        when(callbackDao.findOne(callbackInstance.getId())).thenReturn(callbackInstance);
        when(timeSlotDao.getTime(callbackInstance.getTimeSlotId())).thenReturn(timeSlotInstance);
        when(callbackDao.countDocumentsInTimeSlot(anyLong(), anyLong())).thenReturn(2L);
        when(repDao.count()).thenReturn(30L);
        doNothing().when(callbackDao).updateOne(anyString(), any(Callback.class));
        doNothing().when(mailService).sendConfirmationMail(any(User.class), any(Callback.class));
        callbackEntryService.reschedule(callbackId, callbackInstance);
    }

    @Test(expected = IllegalStateException.class)
    public void rescheduleWithException() throws Exception {
        Callback callbackInstance = getCallbackInstance();
        callbackInstance.setStatus(CallbackStatus.CONFIRMATION_MAIL);
        callbackInstance.setStartTime(System.currentTimeMillis() + 30*60*1000);
        when(callbackDao.findOne(callbackInstance.getId())).thenReturn(callbackInstance);
        callbackEntryService.reschedule(callbackInstance.getId(), callbackInstance);
    }

    @Test(expected = ValidationException.class)
    public void rescheduleWithValidationException() throws Exception {
        Callback callbackInstance = getCallbackInstance();
        TimeSlot timeSlotInstance = getTimeSlotInstance();

        callbackInstance.setStatus(CallbackStatus.CONFIRMATION_MAIL);
        callbackInstance.setStartTime(null);
        callbackInstance.setEndTime(null);
        when(callbackDao.findOne(callbackInstance.getId())).thenReturn(callbackInstance);
        callbackEntryService.reschedule(callbackInstance.getId(), callbackInstance);
    }

    @Test
    public void cancel() throws Exception {
        Callback callbackInstance = getCallbackInstance();
        callbackInstance.setStartTime(callbackInstance.getStartTime());
        TimeSlot timeSlotInstance = getTimeSlotInstance();

        when(callbackDao.findOne(callbackInstance.getId())).thenReturn(callbackInstance);
        doNothing().when(callbackDao).deleteOne(callbackInstance.getId());
        when(callbackDao.getOneWaitingCustomer(anyLong(), anyLong(), any())).thenReturn(callback);
        when(userDao.findOne(anyString())).thenReturn(user);
        when(timeSlotDao.getTime(callbackInstance.getTimeSlotId())).thenReturn(timeSlotInstance);
        doNothing().when(mailService).sendConfirmationMail(any(User.class), any(Callback.class));
        callbackEntryService.cancel(callbackInstance.getId());

    }

    @Test(expected = IllegalStateException.class)
    public void cancelWithinHour() throws Exception {
        Callback callbackInstance = getCallbackInstance();
        callbackInstance.setStatus(CallbackStatus.CONFIRMATION_MAIL);
        callbackInstance.setStartTime(callbackInstance.getStartTime());

        TimeSlot timeSlotInstance = getTimeSlotInstance();
        when(callbackDao.findOne(callbackInstance.getId())).thenReturn(callbackInstance);
        when(timeSlotDao.getTime(callbackInstance.getTimeSlotId())).thenReturn(timeSlotInstance);
        callbackEntryService.cancel(callbackInstance.getId());

    }

    @Test
    public void update() throws Exception {
        doNothing().when(callbackDao).updateOne(anyString(), any(Callback.class));
        callbackEntryService.update(callbackId, callback);
    }

    public Callback getCallbackInstance() {
        Callback callback = new Callback();
        callback.setId(callbackId);
        callback.setUserId(userId);
        callback.setTimeSlotId(timeSlotId);
        callback.setStartTime(System.currentTimeMillis());
        callback.setEndTime(System.currentTimeMillis());
        callback.setStatus(CallbackStatus.NOT_STARTED);
        return callback;
    }

    public TimeSlot getTimeSlotInstance() {
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setId(timeSlotId);
        timeSlot.setStartTime(new Time(System.currentTimeMillis()));
        timeSlot.setEndTime(new Time(System.currentTimeMillis()));
        return timeSlot;
    }

}

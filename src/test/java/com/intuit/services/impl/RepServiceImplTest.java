package com.intuit.services.impl;

import com.intuit.dao.RepDao;
import com.intuit.dao.entities.Callback;
import com.intuit.dao.entities.Rep;
import com.intuit.dao.entities.TimeSlot;
import com.intuit.dao.entities.User;
import com.intuit.dao.impl.RepDaoImpl;
import com.intuit.dao.impl.UserDaoImpl;
import com.intuit.exceptions.ValidationException;
import com.intuit.models.TimeSlotInTimestamp;
import com.intuit.models.requests.UserCreateReq;
import com.intuit.models.responses.RepAssignCallResponse;
import com.intuit.services.CallbackEntryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by Sunil on 9/3/19.
 */

@RunWith(MockitoJUnitRunner.class)
public class RepServiceImplTest {
    private Rep rep;
    private User user;
    private Callback callback;
    private TimeSlotInTimestamp timeSlot;

    @Before
    public void setupMock() {
        user = mock(User.class);
        rep = mock(Rep.class);
        callback = mock(Callback.class);
        timeSlot = mock(TimeSlotInTimestamp.class);
    }

    @Mock
    RepDaoImpl repDao;

    @Mock
    CallbackEntryServiceImpl callbackEntryService;

    @Mock
    UserDaoImpl userDao;

    @InjectMocks
    RepServiceImpl repService;

    @Test
    public void createEntry() throws Exception {
        when(repDao.save(rep)).thenReturn("rep_123");
        String entry = repService.createEntry(rep);
        assertEquals(entry, "rep_123");
    }

    @Test
    public void assignCall() throws Exception {
        when(callbackEntryService.getCustomerInTimeSlot(timeSlot, "rep_123")).thenReturn(callback);
        when(userDao.findOne(callback.getUserId())).thenReturn(user);
        RepAssignCallResponse response = repService.assignCall("rep_123", timeSlot);
        assertEquals(response.getCallback(), callback);
        assertEquals(response.getUser(), user);
    }

    @Test(expected=NoSuchElementException.class)
    public void assignCallWithNoCustomer() throws Exception {
        when(callbackEntryService.getCustomerInTimeSlot(timeSlot, "rep_123")).thenReturn(null);
        given(repService.assignCall("rep_123", timeSlot)).willThrow(new NoSuchElementException("No customer found for the given time range"));
    }

    @Test(expected = ValidationException.class)
    public void assignCallWithNoUserInfo() throws Exception {
        when(callbackEntryService.getCustomerInTimeSlot(timeSlot, "rep_123")).thenReturn(callback);
        when(userDao.findOne(callback.getUserId())).thenThrow(new ValidationException("Not valid id"));
        given(repService.assignCall("rep_123", timeSlot)).willThrow(new ValidationException("Not valid id"));
    }

    @Test
    public void completeCall() throws Exception {
        String repId = "rep_123";
        String callbackId = "callback_123";
        doNothing().when(callbackEntryService).update(anyString(), any(Callback.class));
        repService.completeCall(repId, callbackId);
    }

}

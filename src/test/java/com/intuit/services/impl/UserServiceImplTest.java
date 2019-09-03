package com.intuit.services.impl;

import com.intuit.dao.UserDao;
import com.intuit.dao.entities.User;
import com.intuit.dao.impl.UserDaoImpl;
import com.intuit.models.requests.UserCreateReq;
import com.intuit.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.when;

/**
 * Created by Sunil on 9/3/19.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    private User user;
    private UserCreateReq userCreateReq;

    @Before
    public void setupMock() {
        user = mock(User.class);
        userCreateReq = mock(UserCreateReq.class);
    }

    @Mock
    UserDaoImpl userDao;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    public void createUserSuccess() throws Exception {
        User instance = User.getInstance(userCreateReq);
        when(userDao.save(instance)).thenReturn("user_id_123");
        String userId = userService.createUser(userCreateReq);
        assertEquals(userId, "user_id_123");
    }
}

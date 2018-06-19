package com.tgrajkowski.service;

import com.tgrajkowski.model.model.dao.UserDao;
import com.tgrajkowski.model.user.Users;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserDao userDao;

    @Test
    public void getAllUserTest() {
        // Given
        List<Users> usersList = new ArrayList<>();
        Users users1 = new Users();
        users1.setLogin("login1");
        usersList.add(users1);

        Users users2 = new Users();
        users2.setLogin("login2");
        usersList.add(users2);

        Users users3 = new Users();
        users3.setLogin("login3");
        usersList.add(users3);

        Mockito.when(userDao.findAll()).thenReturn(usersList);
        // When
        List<String> retrieved = userService.getAllUser();
        // Then
        Assert.assertEquals(3, retrieved.size());
        Assert.assertEquals("login1", retrieved.get(0));
        Assert.assertEquals("login2", retrieved.get(1));
        Assert.assertEquals("login3", retrieved.get(2));
    }

    @Test
    public void getAllUsersTestEmptyList() {
        // Given
        List<Users> usersList = new ArrayList<>();
        Mockito.when(userDao.findAll()).thenReturn(usersList);
        //When
        List<String> retrieved = userService.getAllUser();
        // Then
        Assert.assertEquals(0, retrieved.size());
    }
}
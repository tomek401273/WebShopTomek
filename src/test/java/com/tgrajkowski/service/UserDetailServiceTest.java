package com.tgrajkowski.service;

import com.tgrajkowski.model.model.dao.UserDao;
import com.tgrajkowski.model.user.Role;
import com.tgrajkowski.model.user.UserAddress;
import com.tgrajkowski.model.user.UserMapper;
import com.tgrajkowski.model.user.Users;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDetailServiceTest {
    @InjectMocks
    UserDetailService userDetailService;

    @Mock
    UserDao userDao;

    @Test
    public void loadUserByUsername() {
        // Given
        Users user = new Users();
        Role role = new Role();
        role.setId((long)1);
        role.setCode("code");
        role.setName("name");
        role.getUserList().add(user);
        user.getRoleList().add(role);
        UserAddress userAddress = new UserAddress();
        user.setUserAddress(userAddress);
        user.setSurname("surname");
        user.setName("name");
        user.setId((long)1);
        user.setLogin("login");
        user.setPassword("password");
        user.setConfirm(true);
        Mockito.when(userDao.findByLogin("login")).thenReturn(user);
        // When
        UserDetails userDetails = userDetailService.loadUserByUsername("login");
        // Then
        Assert.assertEquals("password", userDetails.getPassword());
        Assert.assertEquals("login",userDetails.getUsername());
        Assert.assertEquals(1, userDetails.getAuthorities().size());
    }

    @Test
    public void loadUserByUsernameNotConfirm() {
        // Given
        Users user = new Users();
        user.setConfirm(false);
        Mockito.when(userDao.findByLogin("login")).thenReturn(user);
        // When
        UserDetails userDetails = userDetailService.loadUserByUsername("login");
        // Then
        Assert.assertNull(userDetails);
    }
}
package com.tgrajkowski.service;

import com.tgrajkowski.model.model.dao.BucketDao;
import com.tgrajkowski.model.model.dao.RoleDao;
import com.tgrajkowski.model.model.dao.UserDao;
import com.tgrajkowski.model.newsletter.ConfirmDto;
import com.tgrajkowski.model.user.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthenticationServiceTest {
    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserDao userDao;

    @Mock
    private RoleDao roleDao;

    @Mock
    private BucketDao bucketDao;

    @Mock
    private SimpleEmailService simpleEmailService;

    private long idOne = 1;

    @Before
    public void initLogin() {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).thenReturn("user-login");
    }

    @Test
    public void singUpTest() {
        // Given
        UserDto userDto = new UserDto();
        Users user = new Users();
        user.setId((long) 1);
        Mockito.when(userMapper.mapToUserPasswordEncode(userDto)).thenReturn(user);
        Role role = new Role();
        role.setName("nameRole");
        role.setCode("codeRole");
        role.setId(idOne);
        Mockito.when(roleDao.findByName("user")).thenReturn(role);
        // When
        UserDto retrieved = authenticationService.singUp(userDto);
        long retrievedId = retrieved.getId();
        // Then
        Assert.assertEquals(idOne, retrievedId);
        Assert.assertEquals(idOne, user.getRoleList().get(0).getId());
        Assert.assertEquals("nameRole", user.getRoleList().get(0).getName());
        Assert.assertEquals("codeRole", user.getRoleList().get(0).getCode());
        Assert.assertFalse(user.isConfirm());
        Assert.assertNotNull(user.getCodeConfirm());
    }

    @Test
    public void signUpTestNull() {
        // Given
        UserDto userDto = new UserDto();
        Mockito.when(userMapper.mapToUserPasswordEncode(userDto)).thenReturn(null);
        // When
        UserDto retrieved = authenticationService.singUp(userDto);
        Assert.assertNull(retrieved);
    }

    @Test
    public void accountUpdateTest() {
        // Given
        Users user = new Users();
        Mockito.when(userDao.findByLogin("user-login")).thenReturn(user);
        Users uploadedUser = new Users();
        uploadedUser.setLogin("new-login");
        uploadedUser.setName("new-name");
        UserDto userDto = new UserDto();
        Mockito.when(userMapper.mapToUserWithoutPassword(userDto, user)).thenReturn(uploadedUser);
        // When
        Users retrieved = authenticationService.accountUpdate(userDto);
        // Then
        Assert.assertEquals("new-login", retrieved.getLogin());
        Assert.assertEquals("new-name", retrieved.getName());
    }

    @Test
    public void passwordUpdateTestOldPasswordCorrect() {
        //Given
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String oldPassword = bCryptPasswordEncoder.encode("oldPassword");
        ChangePassword changePassword = new ChangePassword();
        changePassword.setOldPassword("oldPassword");
        changePassword.setNewPassword("newPassword");
        Users user = new Users();
        user.setPassword(oldPassword);
        Mockito.when(userDao.findByLogin("user-login")).thenReturn(user);
        //When
        boolean retrieved = authenticationService.passwordUpdate(changePassword);
        // Then
        Assert.assertTrue(retrieved);
    }

    @Test
    public void passwordUpdateTestOldPasswordNotCorrect() {
        //Given
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String oldPassword = bCryptPasswordEncoder.encode("oldPassword");
        ChangePassword changePassword = new ChangePassword();
        changePassword.setOldPassword("oldPasswordNotCorrect");
        changePassword.setNewPassword("newPassword");
        Users user = new Users();
        user.setPassword(oldPassword);
        Mockito.when(userDao.findByLogin("user-login")).thenReturn(user);
        //When
        boolean retrieved = authenticationService.passwordUpdate(changePassword);
        // Then
        Assert.assertFalse(retrieved);
    }

    @Test
    public void checkLoginAvailableTest() {
        // Given
        when(userDao.findByLogin("login")).thenReturn(null);
        // Then
        boolean retrieved = authenticationService.checkLoginAvailable("login");
        // When
        Assert.assertTrue(retrieved);
    }

    @Test
    public void checkLoginAvailableTestNot() {
        // Given
        when(userDao.findByLogin("login")).thenReturn(new Users());
        // Then
        boolean retrieved = authenticationService.checkLoginAvailable("login");
        // When
        Assert.assertFalse(retrieved);
    }

    @Test
    public void generateCodeTest() {
        // Given & Then
        String retrieved = authenticationService.generateCode();
        // When
        Assert.assertNotNull(retrieved);
        Assert.assertEquals(23, retrieved.length());
    }

    @Test
    public void accountConfirmTestCorrectCode() {
        // Given
        String email = "useruser@email.com";
        Users user = new Users();
        when(userDao.findByLogin(email)).thenReturn(user);
        ConfirmDto confirmDto = new ConfirmDto();
        confirmDto.setEmail(email);
        confirmDto.setConfirmCode("confirm");
        user.setCodeConfirm("confirm");
        // When
        ConfirmDto retrieved = authenticationService.accountConfirm(confirmDto);
        // Then
        Assert.assertTrue(retrieved.isDiscount());
    }

    @Test
    public void accountConfirmTestWrongCode() {
        // Given
        String email = "useruser@email.com";
        Users user = new Users();
        when(userDao.findByLogin(email)).thenReturn(user);
        ConfirmDto confirmDto = new ConfirmDto();
        confirmDto.setEmail(email);
        confirmDto.setConfirmCode("confirm");
        user.setCodeConfirm("wrongCode");
        // When
        ConfirmDto retrieved = authenticationService.accountConfirm(confirmDto);
        // Then
        Assert.assertFalse(retrieved.isDiscount());
    }
}
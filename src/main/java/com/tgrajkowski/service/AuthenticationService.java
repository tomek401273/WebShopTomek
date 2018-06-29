package com.tgrajkowski.service;

import com.tgrajkowski.app.WebShopConfig;
import com.tgrajkowski.model.bucket.Bucket;
import com.tgrajkowski.model.mail.Mail;
import com.tgrajkowski.model.mail.MailType;
import com.tgrajkowski.model.model.dao.BucketDao;
import com.tgrajkowski.model.model.dao.RoleDao;
import com.tgrajkowski.model.model.dao.UserDao;
import com.tgrajkowski.model.newsletter.ConfirmDto;
import com.tgrajkowski.model.newsletter.RandomString;
import com.tgrajkowski.model.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private BucketDao bucketDao;

    @Autowired
    private SimpleEmailService simpleEmailService;

    public UserDto singUp(UserDto userDto) {
        Users user = userMapper.mapToUserPasswordEncode(userDto);
        if (user != null) {
            List<Role> roles = new ArrayList<>();
            Role role = roleDao.findByName("user");
            roles.add(role);
            user.setRoleList(roles);

            user.setConfirm(false);
            String codeConfirm = generateCode();
            user.setCodeConfirm(codeConfirm);

            userDao.save(user);
            userDto.setId(user.getId());

            Bucket bucket = new Bucket();
            bucket.setUser(user);
            bucketDao.save(bucket);
            sendEmailConfirmAccount(userDto.getName(), userDto.getLogin(), user.getId(), codeConfirm);
            return userDto;
        }
        return null;
    }

    public Users accountUpdate(UserDto userDto) {
        String login = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Users users = userDao.findByLogin(login);
        Users usersUpdated = userMapper.mapToUserWithoutPassword(userDto, users);
        userDao.save(usersUpdated);
        return usersUpdated;
    }

    @Transactional
    public boolean passwordUpdate(ChangePassword changePassword) {
        String login = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Users users = userDao.findByLogin(login);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        if (bCryptPasswordEncoder.matches(changePassword.getOldPassword(), users.getPassword())) {
            String newPassword = bCryptPasswordEncoder.encode(changePassword.getNewPassword());
            users.setPassword(newPassword);
            return true;
        }
        return false;
    }

    public boolean checkLoginAvailable(String login) {
        return userDao.findByLogin(login) == null;
    }

    public void sendEmailConfirmAccount(String username, String email, Long userId, String codeConfirm) {
        String welcome = "Welcome user " + username + " in Computer WebShop";
        String subject = "WebShop Confirm Account";
        String message = "You or someone has attempt to create account in Computer WebShop on: " + new Date() + " using this address " + email;
        String explain = "If you believe that this is a mistake and you did not intend on subscribing to this list, you can ignore this message and nothing else will happen.";
        String goodbye = "Best wishes from Computer WebShop Company";
        Mail mail = new Mail(email, subject, message, MailType.CONFIRM_ACCOUNT);
        mail.setTemplate("account");
        mail.setFragment("confirm");
        mail.setWelcome(welcome);
        mail.setExplain(explain);
        mail.setGoodbye(goodbye);
        mail.setLinkConfirm("confirm-account?email=" + email + "&code-confirm=" + codeConfirm);
        mail.setConfirmAccount(true);
        simpleEmailService.sendMail(mail);
    }

    public String generateCode() {
        String easy = RandomString.digits + "ACEFGHJKLMNPQRUVWXYabcdefhijkprstuvwx";
        RandomString tickets = new RandomString(23, new SecureRandom(), easy);
        return tickets.nextString();
    }

    @Transactional
    public ConfirmDto accountConfirm(ConfirmDto confirmDto) {
        Optional<Users> user =
                Optional.ofNullable(userDao.findByLogin(confirmDto.getEmail()));

        if (user.isPresent()) {
            if (user.get().getCodeConfirm().equals(confirmDto.getConfirmCode())) {
                user.get().setConfirm(true);
                confirmDto.setDiscount(true);
                return confirmDto;
            }
        }
        confirmDto.setDiscount(false);
        return confirmDto;
    }
}

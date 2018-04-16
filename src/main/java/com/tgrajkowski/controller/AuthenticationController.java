package com.tgrajkowski.controller;

import com.tgrajkowski.model.bucket.Bucket;
import com.tgrajkowski.model.model.dao.BucketDao;
import com.tgrajkowski.model.newsletter.ConfirmDto;
import com.tgrajkowski.model.user.*;
import com.tgrajkowski.model.model.dao.RoleDao;
import com.tgrajkowski.model.model.dao.UserDao;
import com.tgrajkowski.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public UserDto singUp(@RequestBody @Valid UserDto userDto) {
        return authenticationService.singUp(userDto);
    }

    @RequestMapping(value = "/checkLoginAvailable", method = RequestMethod.GET)
    public boolean checkLoginAvailable(@RequestParam String login) {
        return authenticationService.checkLoginAvailable(login);
    }

    @RequestMapping(value ="/account/confirm", method = RequestMethod.POST)
    public ConfirmDto accountConfirm(@RequestBody ConfirmDto confirmDto) {
        return authenticationService.accountConfirm(confirmDto);
    }
}

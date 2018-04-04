package com.tgrajkowski.controller;

import com.tgrajkowski.model.bucket.Bucket;
import com.tgrajkowski.model.model.dao.BucketDao;
import com.tgrajkowski.model.user.*;
import com.tgrajkowski.model.model.dao.RoleDao;
import com.tgrajkowski.model.model.dao.UserDao;
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
    AuthenticationManager authenticationManager;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private UserMapper userMapper = new UserMapper();

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private BucketDao bucketDao;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public UserDto singUp(@RequestBody @Valid UserDto userDto) {
        String passwordEncoded = bCryptPasswordEncoder.encode(userDto.getPassword());
        userDto.setPassword(passwordEncoded);
        User user = userMapper.mapToUser(userDto);

        List<Role> roles = new ArrayList<>();
        Role role = roleDao.findByName("user");
        roles.add(role);
        user.setRoleList(roles);

        UserAddress userAddress = new UserAddress(userDto.getCountry(), userDto.getCity(), userDto.getPostCode(), userDto.getStreet());
        user.setUserAddress(userAddress);

        userDao.save(user);
        userDto.setId(user.getId());

        Bucket bucket = new Bucket();
        bucket.setUser(user);
        bucketDao.save(bucket);

        return userDto;
    }

    @RequestMapping(value = "/checkLoginAvailable", method = RequestMethod.POST)
    public boolean checkLoginAvailable(@RequestBody String login) {
        System.out.println(login);
        if (userDao.findByLogin(login) == null) {
            return true;
        }
        return false;
    }

}

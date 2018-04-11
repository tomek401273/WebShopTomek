package com.tgrajkowski.service;

import com.tgrajkowski.model.bucket.Bucket;
import com.tgrajkowski.model.model.dao.BucketDao;
import com.tgrajkowski.model.model.dao.RoleDao;
import com.tgrajkowski.model.model.dao.UserDao;
import com.tgrajkowski.model.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationService {

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

    public UserDto singUp(UserDto userDto) {
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

    public boolean checkLoginAvailable(String login) {
        System.out.println(login);
        if (userDao.findByLogin(login) == null) {
            return true;
        }
        return false;
    }
}

package com.tgrajkowski.service;

import com.tgrajkowski.model.model.dao.RoleDao;
import com.tgrajkowski.model.user.Role;
import com.tgrajkowski.model.user.UserMapper;
import com.tgrajkowski.model.model.dao.UserDao;
import com.tgrajkowski.model.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    UserMapper userMapper = new UserMapper();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username: "+username);
        Users users = userDao.findByLogin(username);
        System.out.println("usersConfirm: "+users.isConfirm());
        if (!users.isConfirm()) {
            return null;
        }
        return userMapper.mapToUserDetails(users);
    }

}

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
        System.out.println("username: " + username);
        Users users = userDao.findByLogin(username);
        System.out.println("Users: " + users.getLogin());

        System.out.println("ROLE DAO");
        List<Role> roleList = roleDao.findAll();
        for (Role role : roleList) {
            System.out.println("role: " + role.getId()+" code: "+ role.getCode());
            List<Users> usersList = role.getUserList();
            for (Users users1 : usersList) {
                System.out.println("login: "+users1.getLogin());
            }
            
        }

        return userMapper.mapToUserDetails(users);
//        return userMapper.mapToUserDetails(userDao.findByLogin(username));
    }

}

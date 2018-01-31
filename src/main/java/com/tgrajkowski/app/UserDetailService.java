package com.tgrajkowski.app;

import com.tgrajkowski.model.UserMapper;
import com.tgrajkowski.model.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    UserDao userDao;

    UserMapper userMapper = new UserMapper();


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userMapper.mapToUserDetails(userDao.findByLogin(username));
    }
}
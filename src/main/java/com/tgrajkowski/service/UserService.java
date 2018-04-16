package com.tgrajkowski.service;

import com.tgrajkowski.model.model.dao.UserDao;
import com.tgrajkowski.model.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class
UserService {
    @Autowired
    UserDao userDao;

    public List<String> getAllUser() {
        List<String> userLoginList = new ArrayList<>();
        List<Users> userList = userDao.findAll();
        for (Users user : userList) {
            userLoginList.add(user.getLogin());
        }
        return userLoginList;
    }
}

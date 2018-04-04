package com.tgrajkowski.service;

import com.tgrajkowski.model.model.dao.UserDao;
import com.tgrajkowski.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public List<String> getAllUser() {
        List<String> userLoginList = new ArrayList<>();
        List<User> userList = userDao.findAll();
        for (User user : userList) {
            userLoginList.add(user.getLogin());
        }
        return userLoginList;
    }
}

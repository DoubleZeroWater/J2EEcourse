package com.example.j2ee.service;

import com.example.j2ee.dataAccessObject.UserDao;
import com.example.j2ee.dataAccessObject.UserDaoImpl;
import com.example.j2ee.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    @Override
    public User getUser(String username, String password) {
        User user = userDao.getUserByUsernameAndPassword(username,password);
        if(user == null)throw new RuntimeException();
        return user;
    }

    @Override
    public List<String> getUsernames() {
        return new ArrayList<>(userDao.getUsernameList());
    }
}

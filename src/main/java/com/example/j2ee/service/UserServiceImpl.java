package com.example.j2ee.service;

import com.example.j2ee.dataAccessObject.UserDao;
import com.example.j2ee.dataAccessObject.UserDaoImpl;
import com.example.j2ee.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUser(String username, String password) {
        User user = userDao.checkPassword(username,password);
        if(user == null)throw new RuntimeException();
        return user;
    }
    @Override
    public User submitUser(String username, String password) {
        User user = userDao.getUserByUsername(username);
        if(user == null){
            user = new User();
            user.setUsername(username);
            user.setPassword(password);
        }
        return user;
    }
    @Override
    public List<String> getUsernames() {
        return new ArrayList<>(userDao.getUsernameList());
    }
}

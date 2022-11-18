package com.example.j2ee.service;

import com.example.j2ee.dataAccessObject.UserDao;
import com.example.j2ee.entity.FullUser;
import com.example.j2ee.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String submitFullUser(FullUser fullUser) {
        User user = userDao.getUserByEmail(fullUser.getEmail());
        if(user.getUsername() == "invalid" && user.getId() == -1){
            return userDao.submitDataBase(fullUser);
        }
        return "Email already exists";
    }
    @Override
    public List<String> getUsernames() {
        return new ArrayList<>(userDao.getUsernameList());
    }
}

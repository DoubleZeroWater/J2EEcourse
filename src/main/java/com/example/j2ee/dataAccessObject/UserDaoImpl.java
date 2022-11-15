package com.example.j2ee.dataAccessObject;

import java.util.ArrayList;
import java.util.List;
import com.example.j2ee.entity.User;

public class UserDaoImpl implements UserDao {
    @Override
    public List<User> getUserList() {
        //模拟从数据库取出两条数据，分别是id=1和id=2的用户，并返回
        User user1 = new User(1,"Jack","qwe");
        User user2 = new User(2,"Mary","asd");
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        return userList;
    }

    @Override
    public List<String> getUsernameList() {
        List<User> userList = getUserList();
        List<String> usernameList = new ArrayList<>();
        for(User user:userList){
            usernameList.add(user.getUsername());
        }
        return usernameList;
    }

    @Override
    public User getUserByUsername(String username) {
        List<User> userList = getUserList();
        for(User user:userList){
            if((user.getUsername()+"").equals(username)){
                return user;
            }
        }
        //如果找不到该用户就返回null
        return null;
    }

    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
        User user = getUserByUsername(username);
        if((user.getPassword()+"").equals(password))return user;
        return null;
    }
}
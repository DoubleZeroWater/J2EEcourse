package com.example.j2ee.dataAccessObject;

import com.example.j2ee.entity.User;
import java.util.List;

public interface UserDao {

    //获取全用户列表
    List<User> getUserList();

    //获取全用户名列表
    List<String> getUsernameList();

    //根据用户名搜索用户
    User getUserByUsername(String username);

    //根据用户名密码搜索用户
    User getUserByUsernameAndPassword(String username,String password);

}

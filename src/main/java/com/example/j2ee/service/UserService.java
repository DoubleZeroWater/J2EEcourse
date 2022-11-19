package com.example.j2ee.service;
import com.example.j2ee.entity.FullUser;
import com.example.j2ee.entity.User;

import java.util.List;


public interface UserService {

        //根据用户名密码获取用户
        User getUser(String username, String password);
        String submitFullUser(FullUser fullUser);

    FullUser getFullUser(String username);

    //获取用户名列表
        List<String> getUsernames();

}

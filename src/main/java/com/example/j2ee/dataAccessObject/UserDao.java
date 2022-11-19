package com.example.j2ee.dataAccessObject;

import com.example.j2ee.entity.FullUser;
import com.example.j2ee.entity.User;

import java.util.List;



public interface UserDao {

    //获取全用户列表
    List<User> getUserList();

    //获取全用户名列表
    List<String> getUsernameList();

    //根据用户名搜索用户
    User getUserByEmail(String username);

    FullUser getFullUserByEmail(String username);
    String submitDataBase(FullUser user);
    //检查密码
    User checkPassword(String username, String password);

    String resetPasswordByEmail(String email, String password);

//    User submitUser(User user);
}

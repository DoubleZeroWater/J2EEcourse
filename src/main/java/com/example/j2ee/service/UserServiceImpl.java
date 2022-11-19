package com.example.j2ee.service;

import com.example.j2ee.dataAccessObject.UserDao;
import com.example.j2ee.entity.FullUser;
import com.example.j2ee.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService
{

    @Autowired
    private UserDao userDao;

    @Override
    public User getUser(String email, String password)
    {
        User user = userDao.checkPassword(email, password);
        if (user == null)
            throw new RuntimeException();
        return user;
    }

    @Override
    public String submitFullUser(FullUser fullUser)
    {
        User user = userDao.getUserByEmail(fullUser.getEmail());
        String rtString = "Email already exists";
        if (user.getUsername() == "invalid" && user.getId() == -1)
        {
            rtString = userDao.submitDataBase(fullUser);
        }
        return rtString;
    }

    @Override
    public FullUser getFullUser(String email)
    {
        FullUser fullUser = userDao.getFullUserByEmail(email);
        return fullUser;
    }

    @Override
    public String resetPassword(String email, String password, String code)
    {
        FullUser fullUser = userDao.getFullUserByEmail(email);
        if (fullUser == null)
            return "Email not exists";
        if (fullUser.getCode().equals(code))
        {
            userDao.resetPasswordByEmail(email, password);
            return "Reset password successfully";
        }
        else
        {
            return "Code is incorrect or not match";
        }
    }
}

package com.example.j2ee.dataAccessObject;

import com.example.j2ee.entity.FullUser;
import com.example.j2ee.entity.User;
import com.example.j2ee.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class UserDaoImpl implements UserDao
{

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<User> getUserList()
    {
        //模拟从数据库取出两条数据，分别是id=1和id=2的用户，并返回
        User user1 = new User(1, "Jack", "qwe");
        User user2 = new User(2, "Mary", "asd");
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        return userList;
    }

    @Override
    public List<String> getUsernameList()
    {
        List<User> userList = getUserList();
        List<String> usernameList = new ArrayList<>();
        for (User user : userList)
        {
            usernameList.add(user.getEmail());
        }
        return usernameList;
    }

    @Override
    public User getUserByEmail(String email)
    {
        String sql = "select * from user where email = ?";
        List<User> userList = jdbcTemplate.query(sql, new Object[]{email}, new UserMapper());
        if (userList.size() == 0)
        {
            return new User(-1, "invalid", "ok");
        }
        return userList.get(0);
    }

    @Override
    public User checkPassword(String email, String password)
    {
        User user = getUserByEmail(email);
        if (user.equals(new User(-1, "invalid", "ok")))
        {
            return new User(-1, "invalid", "ok");
        }
        if ((user.getPassword() + "").equals(password))
        {
            return user;
        }
        return new User(-1, "ok", "invalid");
    }

    @Override
    public String submitDataBase(FullUser fullUser)
    {
        String code = fullUser.getCode();
        String name = fullUser.getName();
        String sql = "select * from activecode where code = ? and name = ?";
        List<String> isAdmin = jdbcTemplate.query(sql, new Object[]{code, name},
                                                  (rs, rowNum) -> rs.getString("isAdmin"));
        if (isAdmin.size() == 0)
        {
            return "Code is incorrect or not match";
        }
        jdbcTemplate.update("delete from activecode where code = ? and name = ?", new Object[]{code, name});
        String sql2 = "insert into user (username, phone, email,school,password,code, name, isAdmin) values (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql2, new Object[]{
                fullUser.getUsername(), fullUser.getPassword(), fullUser.getEmail(), fullUser.getSchool(),
                fullUser.getPassword(), fullUser.getCode(), fullUser.getName(), isAdmin.get(0)});
        return "success";
    }

    @Override
    public FullUser getFullUserByEmail(String email)
    {
        String sql = "select * from user where email = ?";
        List<FullUser> userList = jdbcTemplate.query(sql, new Object[]{email},
                                                     (rs, rowNum) -> new FullUser(
                                                             rs.getInt("id"),
                                                             rs.getString("username"),
                                                             rs.getString("phone"),
                                                             rs.getString("email"),
                                                             rs.getString("school"),
                                                             rs.getString("password"),
                                                             rs.getString("isAdmin"),
                                                             rs.getString("code"),
                                                             rs.getString("name")
                                                     ));
        if (userList.size() == 0)
        {
            return null;
        }
        return userList.get(0);
    }

    @Override
    public String resetPasswordByEmail(String email, String password)
    {
        String sql = "update user set password = ? where email = ?";
        jdbcTemplate.update(sql, new Object[]{password, email});
        return "success";
    }
}
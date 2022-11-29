package com.example.j2ee.dataAccessObject;

import com.example.j2ee.entity.FullUser;
import com.example.j2ee.entity.User;
import com.example.j2ee.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class UserDao
{

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
                fullUser.getPassword(), fullUser.getCode(), fullUser.getName(), isAdmin.get(0)
        });
        return "success";
    }

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

    public String resetPasswordByEmail(String email, String password)
    {
        String sql = "update user set password = ? where email = ?";
        jdbcTemplate.update(sql, new Object[]{password, email});
        return "success";
    }

    public int updateFullUser(FullUser fullUser)
    {
        String sql = "update user set username = ?, phone = ?, email = ?, school = ?, password = ?, code = ?, name = ?, isAdmin = ? where email = ?";
        int result = jdbcTemplate.update(sql, new Object[]{
                fullUser.getUsername(), fullUser.getPhone(), fullUser.getEmail(), fullUser.getSchool(),
                fullUser.getPassword(), fullUser.getCode(), fullUser.getName(), fullUser.getIsAdmin(),
                fullUser.getEmail()
        });
        return result;
    }

    public List<FullUser> getAllUsersDao()
    {
        String sql = "select * from user";
        List<FullUser> userList = jdbcTemplate.query(sql, new Object[]{},
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
        return userList;
    }

    public int deleteUserByEmailDao(String email)
    {
        String sql = "delete from user where email = ?";
        int result = jdbcTemplate.update(sql, new Object[]{email});
        return result;
    }

    public FullUser getFullUserByNameDao(String name)
    {
        String sql = "select * from user where name = ?";
        List<FullUser> fullUserList = jdbcTemplate.query(sql,
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
                                                         ), name);
        if (fullUserList.size() == 0)
        {
            return null;
        }
        return fullUserList.get(0);
    }
}
package com.example.j2ee.dataAccessObject;

import com.example.j2ee.entity.FullUser;
import com.example.j2ee.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<User> getUserList() {
        //模拟从数据库取出两条数据，分别是id=1和id=2的用户，并返回
        User user1 = new User(1, "Jack", "qwe");
        User user2 = new User(2, "Mary", "asd");
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        return userList;
    }

    @Override
    public List<String> getUsernameList() {
        List<User> userList = getUserList();
        List<String> usernameList = new ArrayList<>();
        for (User user : userList) {
            usernameList.add(user.getUsername());
        }
        return usernameList;
    }

    class UserMapper implements RowMapper<User> {
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User student = new User();
            student.setId(rs.getInt("id"));
            student.setUsername(rs.getString("email"));
            student.setPassword(rs.getString("password"));
            return student;
        }
    }


    @Override
    public User getUserByEmail(String email) {
        String sql = "select * from user where email = ?";
        List<User> userList = jdbcTemplate.query(sql, new Object[]{email}, new UserMapper());
        if (userList.size() == 0) {
            return new User(-1, "invalid", "ok");
        }
        return userList.get(0);
    }

    @Override
    public User checkPassword(String email, String password) {
        User user = getUserByEmail(email);
        if (user.equals(new User(-1, "invalid", "ok"))) {
            return new User(-1, "invalid", "ok");
        }
        if ((user.getPassword() + "").equals(password)) {
            return user;
        }
        return new User(-1, "ok", "invalid");
    }

    @Override
    public String submitDataBase(FullUser fullUser) {
        String code = fullUser.getCode();
        String name = fullUser.getName();
        String sql = "delete from activecode where code = ? and name = ?";
        int result = jdbcTemplate.update(sql, new Object[]{code, name});
        if (result == 0) {
            return "Code is incorrect or not match";
        }
        String sql2 = "insert into user (username, phone, email,school,password,code, name,isAdmin) values (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql2, new Object[]{fullUser.getUsername(), fullUser.getPassword(), fullUser.getEmail(), fullUser.getSchool(),
                fullUser.getPassword(), fullUser.getCode(), fullUser.getName(), fullUser.getIsAdmin()});
        return "success";

    }
}
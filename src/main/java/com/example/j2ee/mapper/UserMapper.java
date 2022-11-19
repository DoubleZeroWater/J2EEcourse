package com.example.j2ee.mapper;

import com.example.j2ee.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User>
{
    public User mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        User student = new User();
        student.setId(rs.getInt("id"));
        student.setUsername(rs.getString("email"));
        student.setPassword(rs.getString("password"));
        return student;
    }
}

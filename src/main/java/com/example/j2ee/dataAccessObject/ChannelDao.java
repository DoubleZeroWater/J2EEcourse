package com.example.j2ee.dataAccessObject;

import com.example.j2ee.entity.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChannelDao
{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Channel> getChannelListDao()
    {
        String sql = "select * from channel";
        List<Channel> lst = jdbcTemplate.query(sql, new Object[]{},
                                               (rs, rowNum) -> new Channel(
                                                       rs.getInt("id"),
                                                       rs.getString("name"),
                                                       rs.getString("type"),
                                                       rs.getString("creator"),
                                                       rs.getString("creatorEmail"),
                                                       rs.getInt("score"),
                                                       rs.getDate("due")
                                               ));
        return lst;
    }
}

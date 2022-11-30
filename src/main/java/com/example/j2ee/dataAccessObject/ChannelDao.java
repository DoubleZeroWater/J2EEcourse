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
        List<Channel> lst = jdbcTemplate.query(sql, (rs, rowNum) -> new Channel(rs.getInt("id"), rs.getString("name"),
                                                                                rs.getString("type"),
                                                                                rs.getString("creator"),
                                                                                rs.getString("creatorEmail"),
                                                                                rs.getInt("score"),
                                                                                rs.getTimestamp("due")));
        return lst;
    }

    public int getChannelIdByNameDao(String name)
    {
        String sql = "select * from channel where name = ?";
        List<Channel> lst = jdbcTemplate.query(sql, (rs, rowNum) -> new Channel(rs.getInt("id"), rs.getString("name"),
                                                                                rs.getString("type"),
                                                                                rs.getString("creator"),
                                                                                rs.getString("creatorEmail"),
                                                                                rs.getInt("score"),
                                                                                rs.getTimestamp("due")), name);
        if (lst.size() == 0)
        {
            return 0;
        }
        return lst.get(0).getId();
    }

    public Channel updateChannel(Channel channel)
    {
        String sql = "update channel set name = ?, type = ?, creator = ?, creatorEmail = ?, score = ?, due = ? where id = ?";
        jdbcTemplate.update(sql, channel.getName(), channel.getType(), channel.getCreator(), channel.getCreatorEmail(),
                            channel.getScore(), channel.getDue(), channel.getId());
        return channel;
    }

    public int addChannel(Channel channel)
    {
        String sql = "insert into channel (`name`, `type`, `creator`, `creatorEmail`, `score`, `due`) values (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, channel.getName(), channel.getType(), channel.getCreator(),
                                   channel.getCreatorEmail(), channel.getScore(), channel.getDue());
    }

    public Channel getChannelByNameDao(String name)
    {
        String sql = "select * from channel where name = ?";
        List<Channel> lst = jdbcTemplate.query(sql, new Object[]{name},
                                               (rs, rowNum) -> new Channel(rs.getInt("id"), rs.getString("name"),
                                                                           rs.getString("type"),
                                                                           rs.getString("creator"),
                                                                           rs.getString("creatorEmail"),
                                                                           rs.getInt("score"), rs.getTimestamp("due")));
        if (lst.size() == 0)
        {
            return null;
        }
        return lst.get(0);
    }

}

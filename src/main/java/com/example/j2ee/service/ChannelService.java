package com.example.j2ee.service;

import com.example.j2ee.dataAccessObject.ChannelDao;
import com.example.j2ee.entity.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChannelService
{
    @Autowired
    private ChannelDao channelDao;

    public List<Channel> getAllChannelList()
    {
        return channelDao.getChannelListDao();
    }

    public Channel updateChannel(Channel channel)
    {
        return channelDao.updateChannel(channel);
    }

    public Channel getChannelByName(String name)
    {
        return channelDao.getChannelByNameDao(name);
    }

    public int addChannel(Channel channel)
    {
        return channelDao.addChannel(channel);
    }

    public Channel getChannelById(int id)
    {
        return channelDao.getChannelByIdDao(id);
    }
}

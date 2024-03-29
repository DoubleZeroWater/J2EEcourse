package com.example.j2ee.service;

import com.example.j2ee.dataAccessObject.ChannelDao;
import com.example.j2ee.dataAccessObject.ProjectDao;
import com.example.j2ee.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class ProjectService
{
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private ChannelDao channelDao;

    public int uploadProject(Project project, InputStream fig, InputStream file)
    {
        return projectDao.uploadProjectDao(project, fig, file);
    }

    public int updateProject(Project project, InputStream isFig, InputStream isZip)
    {
        return projectDao.updateProjectDao(project, isFig, isZip);
    }

    public List<Project> getProjectByName(String name)
    {
        return projectDao.getProjectByNameDao(name);
    }


    public List<Project> getProjectByEmail(String uploaderEmail)
    {
        return projectDao.getProjectByEmailDao(uploaderEmail);
    }

    public InputStream getFig(int id)
    {
        return projectDao.getFigDao(id);
    }

    public InputStream getZip(int id)
    {
        return projectDao.getZipDao(id);
    }

    public String getProjectEmailById(int id)
    {
        return projectDao.getProjectEmailByIdDao(id);
    }

    public String getProjectNameById(int id)
    {
        return projectDao.getProjectNameByIdDao(id);
    }

    public int deleteProject(int id)
    {
        return projectDao.deleteProjectDao(id);
    }

    public List<Project> getAllProject()
    {
        return projectDao.getAllProjectDao();
    }

    public int isExist(int id)
    {
        return projectDao.isExistDao(id);
    }

    public int getScoreByEmail(String email)
    {
        List<Project> projects = projectDao.getProjectByEmailDao(email);
        int score = 0;
        for (Project project : projects)
        {
            int channel = 0;
            channel = project.getChannelId();
            score += channelDao.getChannelByIdDao(channel).getScore();
        }
        return score;
    }

    public int changeStatus(int id, String status)
    {
        return projectDao.changeStatusDao(id, status);
    }

    public Project getProjectById(int id)
    {
        return projectDao.getProjectByIdDao(id);
    }
}

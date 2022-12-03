package com.example.j2ee.service;

import com.example.j2ee.dataAccessObject.ThesisDao;
import com.example.j2ee.entity.Thesis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class ThesisService
{
    @Autowired
    private ThesisDao thesisDao;

    public int uploadThesis(Thesis thesis, InputStream isFig, InputStream isZip)
    {
        return thesisDao.uploadThesisDao(thesis, isFig, isZip);
    }

    public void updateThesis(Thesis thesis, InputStream isFig, InputStream isZip)
    {
        thesisDao.updateThesisDao(thesis, isFig, isZip);
    }

    public String getEmailById(int id)
    {
        return thesisDao.getThesisEmailByIdDao(id);
    }

    public int deleteProject(int id)
    {
        return thesisDao.deleteThesisDao(id);
    }

    public List<Thesis> queryThesisByName(String name)
    {
        return thesisDao.queryThesisByNameDao(name);
    }

    public List<Thesis> queryThesisByUploaderEmail(String uploaderEmail)
    {
        return thesisDao.queryThesisByUploaderEmailDao(uploaderEmail);
    }

    public List<Thesis> queryAllThesis()
    {
        return thesisDao.queryAllThesisDao();
    }

    public String getThesisNameById(int id)
    {
        return thesisDao.getThesisNameByIdDao(id);
    }

    public InputStream getZip(int id)
    {
        return thesisDao.getZipDao(id);
    }

    public InputStream getFig(int id)
    {
        return thesisDao.getFigDao(id);
    }

    public int isExist(int id)
    {
        return thesisDao.isExistDao(id);
    }
}

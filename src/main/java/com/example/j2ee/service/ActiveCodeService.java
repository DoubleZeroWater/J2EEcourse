package com.example.j2ee.service;

import com.example.j2ee.dataAccessObject.ActiveCodeDao;
import com.example.j2ee.entity.ActiveCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActiveCodeService
{
    @Autowired
    private ActiveCodeDao activeCodeDao;

    public int addActiveCode(String code, String name, String isAdmin)
    {
        return activeCodeDao.addActiveCodeDao(code, name, isAdmin);
    }

    public List<ActiveCode> getActiveCode()
    {
        return activeCodeDao.getActiveCodeListDao();
    }
}

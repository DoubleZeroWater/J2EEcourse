package com.example.j2ee.dataAccessObject;

import com.example.j2ee.entity.ActiveCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ActiveCodeDao
{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int addActiveCodeDao(String code, String name, String isAdmin)
    {
        String sql = "insert into activecode (code, name, isAdmin) values (?, ?, ?)";
        return jdbcTemplate.update(sql, new Object[]{code, name, isAdmin});
    }

    public List<ActiveCode> getActiveCodeListDao()
    {
        String sql = "select * from activecode";
        List<ActiveCode> lst = jdbcTemplate.query(sql, new Object[]{},
                                                  (rs, rowNum) -> new ActiveCode(
                                                          rs.getString("code"),
                                                          rs.getString("name"),
                                                          rs.getString("isAdmin")
                                                  ));
        return lst;
    }


}

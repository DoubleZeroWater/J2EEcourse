package com.example.j2ee.dataAccessObject;

import com.example.j2ee.entity.Thesis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.util.List;

@Repository
public class ThesisDao
{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int uploadThesisDao(Thesis thesis, InputStream isFig, InputStream isZip)
    {
        String sql = "INSERT INTO thesis (`name`, `uploaderEmail`, `maintainer`, `channelId`, `description`, `company`, `submitdate`, `status` , `fig`,`zip`) VALUES (?, ?, ?, ?,?,?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, thesis.getName(), thesis.getUploaderEmail(), thesis.getMaintainer(),
                                   thesis.getChannelId(), thesis.getDescription(), thesis.getCompany(),
                                   thesis.getSubmitTime(), thesis.getStatus(), isFig, isZip);
    }

    public void updateThesisDao(Thesis thesis, InputStream isFig, InputStream isZip)
    {
        if (isFig == null && isZip == null)
        {
            String sql = "UPDATE thesis SET `name` = ?, `uploaderEmail` = ?, `maintainer` = ?, `channelId` = ?, `description` = ?, `company` = ?, `submitdate` = ?, `status` = ? WHERE id = ?";
            jdbcTemplate.update(sql, thesis.getName(), thesis.getUploaderEmail(), thesis.getMaintainer(),
                                thesis.getChannelId(), thesis.getDescription(), thesis.getCompany(),
                                thesis.getSubmitTime(), thesis.getStatus(), thesis.getId());
        }
        else if (isFig == null && isZip != null)
        {
            String sql = "UPDATE thesis SET `name` = ?, `uploaderEmail` = ?, `maintainer` = ?, `channelId` = ?, `description` = ?, `company` = ?, `submitdate` = ?, `status` = ? , `zip` = ? WHERE id = ?";
            jdbcTemplate.update(sql, thesis.getName(), thesis.getUploaderEmail(), thesis.getMaintainer(),
                                thesis.getChannelId(), thesis.getDescription(), thesis.getCompany(),
                                thesis.getSubmitTime(), thesis.getStatus(), isZip, thesis.getId());
        }
        else if (isFig != null && isZip == null)
        {
            String sql = "UPDATE thesis SET `name` = ?, `uploaderEmail` = ?, `maintainer` = ?, `channelId` = ?, `description` = ?, `company` = ?, `submitdate` = ?, `status` = ? , `fig` = ? WHERE id = ?";
            jdbcTemplate.update(sql, thesis.getName(), thesis.getUploaderEmail(), thesis.getMaintainer(),
                                thesis.getChannelId(), thesis.getDescription(), thesis.getCompany(),
                                thesis.getSubmitTime(), thesis.getStatus(), isFig, thesis.getId());
        }
        else
        {
            String sql = "UPDATE thesis SET `name` = ?, `uploaderEmail` = ?, `maintainer` = ?, `channelId` = ?, `description` = ?, `company` = ?, `submitdate` = ?, `status` = ? , `fig` = ?, `zip` = ? WHERE id = ?";
            jdbcTemplate.update(sql, thesis.getName(), thesis.getUploaderEmail(), thesis.getMaintainer(),
                                thesis.getChannelId(), thesis.getDescription(), thesis.getCompany(),
                                thesis.getSubmitTime(), thesis.getStatus(), isFig, isZip, thesis.getId());
        }
    }

    public String getThesisEmailByIdDao(int id)
    {
        String sql = "SELECT uploaderEmail FROM thesis WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, id);
    }

    public int deleteThesisDao(int id)
    {
        String sql = "DELETE FROM thesis WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public List<Thesis> queryThesisByNameDao(String name)
    {
        String sql = "select * from thesis where name = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Thesis(rs.getInt("id"), rs.getString("uploaderEmail"),
                                                                  rs.getString("name"),
                                                                  rs.getString("maintainer"), rs.getInt("channelId"),
                                                                  rs.getString("description"), rs.getString("company"),
                                                                  rs.getTimestamp("submitdate"),
                                                                  rs.getString("status")), name);
    }

    public List<Thesis> queryThesisByUploaderEmailDao(String uploaderEmail)
    {
        String sql = "select * from thesis where uploaderEmail = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Thesis(rs.getInt("id"), rs.getString("uploaderEmail"),
                                                                  rs.getString("name"),
                                                                  rs.getString("maintainer"), rs.getInt("channelId"),
                                                                  rs.getString("description"), rs.getString("company"),
                                                                  rs.getTimestamp("submitdate"),
                                                                  rs.getString("status")), uploaderEmail);
    }

    public List<Thesis> queryAllThesisDao()
    {
        String sql = "select * from thesis";
        return jdbcTemplate.query(sql
                , (rs, rowNum) -> new Thesis(rs.getInt("id"), rs.getString("uploaderEmail"),
                                             rs.getString("name"),
                                             rs.getString("maintainer"), rs.getInt("channelId"),
                                             rs.getString("description"), rs.getString("company"),
                                             rs.getTimestamp("submitdate"),
                                             rs.getString("status")));
    }

    public String getThesisNameByIdDao(int id)
    {
        String sql = "SELECT name FROM thesis WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, id);
    }

    public InputStream getZipDao(int id)
    {
        String sql = "SELECT zip FROM thesis WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getBlob("zip").getBinaryStream(), id);
    }

    public InputStream getFigDao(int id)
    {
        String sql = "SELECT fig FROM thesis WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getBlob("fig").getBinaryStream(), id);
    }

    public int isExistDao(int id)
    {
        String sql = "SELECT COUNT(*) FROM thesis WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, id);
    }

    public void changeStatusDao(int id, String toString)
    {
        String sql = "UPDATE thesis SET `status` = ? WHERE id = ?";
        jdbcTemplate.update(sql, toString, id);
    }

    public Thesis queryThesisByIdDao(int id)
    {
        String sql = "SELECT * FROM thesis WHERE id = ?";
        return jdbcTemplate.queryForObject(sql,
                                           (rs, rowNum) -> new Thesis(rs.getInt("id"), rs.getString("uploaderEmail"),
                                                                      rs.getString("name"),
                                                                      rs.getString("maintainer"),
                                                                      rs.getInt("channelId"),
                                                                      rs.getString("description"),
                                                                      rs.getString("company"),
                                                                      rs.getTimestamp("submitdate"),
                                                                      rs.getString("status")), id);
    }
}

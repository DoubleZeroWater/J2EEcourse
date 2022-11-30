package com.example.j2ee.dataAccessObject;

import com.example.j2ee.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.util.List;

@Repository
public class ProjectDao
{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int uploadProjectDao(Project project, InputStream fig, InputStream file)
    {
        String sql = "insert into project (`uploaderemail`, `name`, `maintainer`, `channelid`, `description`, `company`, `money`, `submitdate`, `status`, `fig`, `zip`) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, project.getUploaderEmail(), project.getName(), project.getMaintainer(),
                                   project.getChannelId(), project.getDescription(), project.getCompany(),
                                   project.getMoney(), project.getSetTime(), project.getStatus(), fig, file);
    }

    public List<Integer> getIdByNameDao(String name)
    {
        String sql = "select id from project where name = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> (rs.getInt("id")), name);
    }

    public int updateProjectDao(Project project, InputStream isFig, InputStream isZip)
    {

        if (isFig == null && isZip == null)
        {
            String sql = "update project set `uploaderemail` = ?, `name` = ?, `maintainer` = ?, `channelid` = ?, `description` = ?, `company` = ?, `money` = ?, `submitdate` = ?, `status` = ? where id = ?";
            jdbcTemplate.update(sql, project.getUploaderEmail(), project.getName(), project.getMaintainer(),
                                project.getChannelId(), project.getDescription(), project.getCompany(),
                                project.getMoney(), project.getSetTime(), project.getStatus(), project.getId());

        }
        else if (isFig == null && isZip != null)
        {
            String sql = "update project set `uploaderemail` = ?, `name` = ?, `maintainer` = ?, `channelid` = ?, `description` = ?, `company` = ?, `money` = ?, `submitdate` = ?, `status` = ? ,`zip` = ? where id = ?";
            jdbcTemplate.update(sql, project.getUploaderEmail(), project.getName(), project.getMaintainer(),
                                project.getChannelId(), project.getDescription(), project.getCompany(),
                                project.getMoney(), project.getSetTime(), project.getStatus(), isZip, project.getId());
        }
        else if (isFig != null && isZip == null)
        {
            String sql = "update project set `uploaderemail` = ?, `name` = ?, `maintainer` = ?, `channelid` = ?, `description` = ?, `company` = ?, `money` = ?, `submitdate` = ?, `status` = ? ,`fig` = ? where id = ?";
            jdbcTemplate.update(sql, project.getUploaderEmail(), project.getName(), project.getMaintainer(),
                                project.getChannelId(), project.getDescription(), project.getCompany(),
                                project.getMoney(), project.getSetTime(), project.getStatus(), isFig, project.getId());
        }
        else
        {
            String sql = "update project set `uploaderemail` = ?, `name` = ?, `maintainer` = ?, `channelid` = ?, `description` = ?, `company` = ?, `money` = ?, `submitdate` = ?, `status` = ? ,`fig` = ?,`zip` = ? where id = ?";
            jdbcTemplate.update(sql, project.getUploaderEmail(), project.getName(), project.getMaintainer(),
                                project.getChannelId(), project.getDescription(), project.getCompany(),
                                project.getMoney(), project.getSetTime(), project.getStatus(), isFig, isZip,
                                project.getId());
        }
        return 1;
    }

    public List<Project> getProjectByNameDao(String name)
    {
        String sql = "select * from project where name = ?";
        return jdbcTemplate.query(sql,
                                  (rs, rowNum) -> new Project(rs.getInt("id"), rs.getString("uploaderemail"),
                                                              rs.getString("name"), rs.getString("maintainer"),
                                                              rs.getInt("channelid"),
                                                              rs.getString("description"),
                                                              rs.getString("company"), rs.getInt("money"),
                                                              rs.getTimestamp("submitdate"),
                                                              rs.getString("status")), name);
    }

    public List<Project> getProjectByEmailDao(String uploaderEmail)
    {
        String sql = "select * from project where uploaderemail = ?";
        return jdbcTemplate.query(sql,
                                  (rs, rowNum) -> new Project(rs.getInt("id"), rs.getString("uploaderemail"),
                                                              rs.getString("name"), rs.getString("maintainer"),
                                                              rs.getInt("channelid"),
                                                              rs.getString("description"),
                                                              rs.getString("company"), rs.getInt("money"),
                                                              rs.getTimestamp("submitdate"),
                                                              rs.getString("status")), uploaderEmail);
    }

    public InputStream getFigDao(int id)
    {
        String sql = "select fig from project where id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getBlob("fig").getBinaryStream(), id);
    }

    public InputStream getZipDao(int id)
    {
        String sql = "select zip from project where id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getBlob("zip").getBinaryStream(), id);
    }

    public String getProjectEmailByIdDao(int id)
    {
        String sql = "select uploaderemail from project where id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getString("uploaderemail"), id);
    }

    public String getProjectNameByIdDao(int id)
    {
        String sql = "select name from project where id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getString("name"), id);
    }

    public int deleteProjectDao(int id)
    {
        String sql = "delete from project where id = ?";
        return jdbcTemplate.update(sql, id);
    }
}

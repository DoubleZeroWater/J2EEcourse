package com.example.j2ee.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import java.sql.Date;

@Schema(name = "Channel", description = "通道信息")
public class Channel
{
    @Schema(name = "id", description = "数据表自增id")
    private int id;
    @Schema(name = "name", description = "通道名称")
    private String name;
    @Schema(name = "type", description = "通道类型(T代表论文通道，P代表项目通道)", allowableValues = "T,P")
    private String type;
    @Schema(name = "creator", description = "创建者")
    private String creator;
    @Schema(name = "creatorEmail", description = "创建者邮箱")
    private String creatorEmail;
    @Schema(name = "score", description = "对应得分")
    private int score;
    @Schema(name = "due", description = "截止时间")
    private Date due;

    public Channel()
    {
    }

    public Channel(int id, String name, String type, String creator, String creatorEmail, int score, Date due)
    {
        this.id = id;
        this.name = name;
        this.type = type;
        this.creator = creator;
        this.creatorEmail = creatorEmail;
        this.score = score;
        this.due = due;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getCreator()
    {
        return creator;
    }

    public void setCreator(String creator)
    {
        this.creator = creator;
    }

    public String getCreatorEmail()
    {
        return creatorEmail;
    }

    public void setCreatorEmail(String creatorEmail)
    {
        this.creatorEmail = creatorEmail;
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public Date getDue()
    {
        return due;
    }

    public void setDue(Date due)
    {
        this.due = due;
    }


}

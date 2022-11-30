package com.example.j2ee.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Schema(name = "Project", description = "项目")
@Getter
@Setter
public class Project
{
    @Schema(name = "id", description = "数据表自增id")
    private int id;
    @Schema(name = "name", description = "项目名称")
    private String name;
    @Schema(name = "uploaderEmail", description = "上传者邮箱")
    private String uploaderEmail;
    @Schema(name = "maintainer", description = "负责人姓名")
    private String maintainer;
    @Schema(name = "channelId", description = "上传的通道ID")
    private int channelId;
    @Schema(name = "description", description = "项目描述")
    private String description;
    @Schema(name = "company", description = "负责单位")
    private String company;
    @Schema(name = "money", description = "金额")
    private int money;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(name = "setTime", description = "上传时间")
    private Timestamp setTime;
    @Schema(name = "status", description = "项目状态", allowableValues = "Waiting, Accept, Reject")
    private String status;


    public Project()
    {
    }

    public Project(String uploaderEmail, String name, String maintainer, int channelId, String description,
                   String company, int money, Timestamp setTime, String status)
    {
        this.uploaderEmail = uploaderEmail;
        this.name = name;
        this.maintainer = maintainer;
        this.channelId = channelId;
        this.description = description;
        this.company = company;
        this.money = money;
        this.setTime = setTime;
        this.status = status;
    }

    public Project(int id, String uploaderEmail, String name, String maintainer, int channelId, String description,
                   String company, int money, Timestamp setTime, String status)
    {
        this.id = id;
        this.uploaderEmail = uploaderEmail;
        this.name = name;
        this.maintainer = maintainer;
        this.channelId = channelId;
        this.description = description;
        this.company = company;
        this.money = money;
        this.setTime = setTime;
        this.status = status;
    }


    public enum ProjectStatus
    {
        Waiting, Accept, Reject
    }


}

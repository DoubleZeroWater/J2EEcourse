package com.example.j2ee.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Schema(name = "Thesis", description = "论文")
@Getter
@Setter
public class Thesis
{
    @Schema(name = "id", description = "数据表自增id")
    private int id;
    @Schema(name = "name", description = "论文名称")
    private String name;
    @Schema(name = "uploaderEmail", description = "上传者邮箱")
    private String uploaderEmail;

    @Schema(name = "maintainer", description = "负责人姓名")
    private String maintainer;

    @Schema(name = "channelId", description = "上传的通道ID")
    private int channelId;
    @Schema(name = "description", description = "论文描述")
    private String description;


    @Schema(name = "company", description = "负责单位")
    private String company;

    @Schema(name = "submitTime", description = "提交时间")
    private Timestamp submitTime;

    @Schema(name = "status", description = "状态", allowableValues = "Waiting, Accept, Reject")
    private String status;

    public enum ThesisStatus
    {
        Waiting, Accept, Reject
    }

    public Thesis()
    {
    }

    public Thesis(String uploaderEmail, String name, String maintainer, int channelId, String description,
                  String company, Timestamp submitTime, String status)
    {
        this.uploaderEmail = uploaderEmail;
        this.name = name;
        this.maintainer = maintainer;
        this.channelId = channelId;
        this.description = description;
        this.company = company;
        this.submitTime = submitTime;
        this.status = status;
    }

    public Thesis(int id, String uploaderEmail, String name, String maintainer, int channelId, String description,
                  String company, Timestamp submitTime, String status)
    {
        this.id = id;
        this.uploaderEmail = uploaderEmail;
        this.name = name;
        this.maintainer = maintainer;
        this.channelId = channelId;
        this.description = description;
        this.company = company;
        this.submitTime = submitTime;
        this.status = status;
    }
}

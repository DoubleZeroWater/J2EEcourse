package com.example.j2ee.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(name = "Channel", description = "通道信息")
public class Channel
{
    @Schema(name = "id", description = "数据表自增id")
    private int id;
    @Schema(name = "name", description = "通道名称")
    private String name;
    @Schema(name = "type", description = "通道类型(Thesis代表论文通道，Project代表项目通道)")
    private String type;
    @Schema(name = "creator", description = "创建者")
    private String creator;
    @Schema(name = "creatorEmail", description = "创建者邮箱")
    private String creatorEmail;
    @Schema(name = "score", description = "对应得分")
    private int score;
    @Schema(name = "due", description = "截止时间")
    private LocalDateTime due;

    public Channel()
    {
        this.id = 0;
    }

    public Channel(int id, String name, String type, String creator, String creatorEmail, int score, LocalDateTime due)
    {
        this.id = id;
        this.name = name;
        this.type = type;
        this.creator = creator;
        this.creatorEmail = creatorEmail;
        this.score = score;
        this.due = due;
    }

    public enum ChannelType
    {
        Thesis, Project
    }
}

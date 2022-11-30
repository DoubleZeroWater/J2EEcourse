package com.example.j2ee.controller;


import com.example.j2ee.entity.Channel;
import com.example.j2ee.service.ChannelService;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.List;

@RestController
@Tag(name = "通道信息")
public class ChannelController
{
    private final HttpSession session;
    @Autowired
    private ChannelService channelService;


    public ChannelController(HttpSession session)
    {
        this.session = session;
    }

    @Operation(summary = "获取所有通道信息")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content()),
            })
    @GetMapping("/listChannel")
    public ResponseEntity<List<Channel>> listChannel()
    {
        if (session.getAttribute("email") != null)
        {
            return ResponseEntity.status(200).body(channelService.getAllChannelList());
        }
        else
        {
            return ResponseEntity.status(403).body(null);
        }
    }

    @Operation(summary = "根据通道名称获取通道信息")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content()),
            })
    @GetMapping("/getChannelByName")
    public ResponseEntity<Channel> getChannelByName(@Parameter(description = "通道名称") @RequestParam String name)
    {
        if (session.getAttribute("email") != null)
        {
            Channel rtChannel = channelService.getChannelByName(name);
            if (rtChannel != null)
                return ResponseEntity.status(200).body(rtChannel);
            else
                return ResponseEntity.status(404).body(null);
        }
        else
        {
            return ResponseEntity.status(403).body(null);
        }
    }

    @Operation(summary = "更新通道信息", description = "根据通道名称更新通道信息，可以更新除了id以外的所有信息")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content()),
                    @ApiResponse(responseCode = "400", description = "Server Error", content = @Content()),
            })
    @PostMapping("/updateChannel")
    public ResponseEntity<Channel> updateChannel(@Parameter(description = "通道名称") @RequestParam String name,
                                                 @Parameter(description = "通道类型") @RequestParam
                                                 Channel.ChannelType type,
                                                 @Parameter(description = "通道创建者") @RequestParam String creator,
                                                 @Parameter(description = "通道创建者邮箱") @RequestParam
                                                 String creatorEmail,
                                                 @Parameter(description = "通道得分") @RequestParam int score,
                                                 @Parameter(description = "通道截止时间(yyyy-MM-dd HH:mm:ss)")
                                                 @RequestParam
                                                 @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
                                                 Timestamp due)
    {
        try
        {
            if (session.getAttribute("isAdmin") != null && session.getAttribute("isAdmin").equals("1"))
            {
                Channel tmpChannel = new Channel(0, name, type.toString(), creator, creatorEmail, score, due);
                Channel rtChannel = channelService.updateChannel(tmpChannel);
                if (rtChannel.getId() == 0)
                {
                    return ResponseEntity.status(404).body(null);
                }
                else
                {
                    return ResponseEntity.status(200).body(rtChannel);
                }
            }
            else
            {
                return ResponseEntity.status(403).body(null);
            }
        } catch (RuntimeException e)
        {
            return ResponseEntity.status(400).body(null);
        }

    }

    @Operation(summary = "添加通道信息", description = "添加通道信息，可以更新除了id以外的所有信息")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content()),
                    @ApiResponse(responseCode = "400", description = "Server Error", content = @Content()),
            })
    @PutMapping("/addChannel")
    public ResponseEntity<Void> addChannel(@Parameter(description = "通道名称") @RequestParam String name,
                                           @Parameter(description = "通道类型") @RequestParam
                                           Channel.ChannelType type,
                                           @Parameter(description = "通道创建者") @RequestParam String creator,
                                           @Parameter(description = "通道创建者邮箱") @RequestParam
                                           String creatorEmail,
                                           @Parameter(description = "通道得分") @RequestParam int score,
                                           @Parameter(description = "通道截止时间(yyyy-MM-dd HH:mm:ss)")
                                           @RequestParam
                                           @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
                                           Timestamp due)
    {
        try
        {
            if (session.getAttribute("isAdmin") != null && session.getAttribute("isAdmin").equals("1"))
            {
                Channel tmpChannel = new Channel(0, name, type.toString(), creator, creatorEmail, score, due);
                int rtChannel = channelService.addChannel(tmpChannel);
                if (rtChannel == 0)
                {
                    return ResponseEntity.status(400).body(null);
                }
                else
                {
                    return ResponseEntity.status(200).body(null);
                }
            }
            else
            {
                return ResponseEntity.status(403).body(null);
            }
        } catch (RuntimeException e)
        {
            return ResponseEntity.status(400).body(null);
        }

    }
}

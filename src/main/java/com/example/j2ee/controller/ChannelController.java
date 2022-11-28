package com.example.j2ee.controller;


import com.example.j2ee.entity.Channel;
import com.example.j2ee.service.ChannelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
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

}

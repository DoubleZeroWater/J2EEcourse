package com.example.j2ee.controller;

import com.example.j2ee.entity.FullUser;
import com.example.j2ee.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@Api(tags = "用户接口")
public class UserController
{
    private final HttpSession session;

    public UserController(HttpSession session)
    {
        this.session = session;
    }

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用邮件查询用户信息")
    @GetMapping("/getUsernames/{email}")
    @ApiResponses(
            {
                    @ApiResponse(code = 200, message = "Success", response = FullUser.class),
                    @ApiResponse(code = 403, message = "Not login"),
            })
    public ResponseEntity getFullUserInfo(
            @ApiParam(value = "需要用户登录,session验证", required = true) @PathVariable String email)
    {
        if (session.getAttribute("email") == null)
        {
            return ResponseEntity.status(403).body("Not login");
        }
        else
        {
            return ResponseEntity.status(200).body(userService.getFullUser(email));
        }
    }

    @ApiOperation(value = "更新用户数据")
    @PostMapping("/updateUser")
    @ApiResponses(
            {
                    @ApiResponse(code = 200, message = "Success", response = String.class),
                    @ApiResponse(code = 210, message = "Can't find email", response = String.class),
                    @ApiResponse(code = 403, message = "Not login"),
                    @ApiResponse(code = 400, message = "Server Error"),
            })
    public ResponseEntity updateFullUserInfo(
            @ApiParam(value = "不能修改姓名，注册码，权限，邮箱", required = true) @RequestBody
            FullUser fullUser)
    {
        try
        {
            if (session.getAttribute("email") == null)
            {
                return ResponseEntity.status(403).body("Not login");
            }
            else
            {
                int result = userService.updateFullUserInfo(fullUser);
                if (result == 0)
                {
                    return ResponseEntity.status(210).body("Can't find email");
                }
                else
                {
                    return ResponseEntity.status(200).body("Success");
                }
            }
        } catch (Exception e)
        {
            System.out.println(e);
            return ResponseEntity.status(400).body("Server Error");
        }
    }
}
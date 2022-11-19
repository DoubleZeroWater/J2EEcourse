package com.example.j2ee.controller;

import com.example.j2ee.entity.FullUser;
import com.example.j2ee.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;

@RestController
@Api(tags = "用户接口")
public class UserController
{
    @Autowired
    private UserService userService;

    @ApiOperation(value = "用邮件查询用户信息(需要用户登录,session验证)")
    @GetMapping("/getUsernames/{email}")
    @ApiResponses(
            {
                    @ApiResponse(code = 200, message = "Success", response = FullUser.class),
                    @ApiResponse(code = 403, message = "Not login"),
            })
    public ResponseEntity getUserInfo(@ApiParam(value = "email", required = true) @PathVariable String email,
                                      @ApiIgnore HttpSession session)
    {
        //check session
        int a = 0;
        a += 1;

        if (session.getAttribute("email") == null)
        {
            return ResponseEntity.status(403).body("Not login");
        }
        else
        {
            return ResponseEntity.status(200).body(userService.getFullUser(email));
        }
    }

}
package com.example.j2ee.controller;

import com.example.j2ee.entity.User;
import com.example.j2ee.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;

@RestController
@Api("Login Interface")
public class LoginController {

    @Autowired
    private UserService userService;

    @ApiOperation("Login")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "邮箱", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "Login success", response = User.class),
            @ApiResponse(code = 210, message = "Can't find email", response = String.class),
            @ApiResponse(code = 220, message = "Wrong password", response = String.class),
            @ApiResponse(code = 400, message = "Server Error"),
    })
    @GetMapping("/login/{username}/{password}")
    public ResponseEntity login(@PathVariable(value = "username",required = true)String username,
                        @PathVariable(value = "password",required = true)String password,
                        @ApiIgnore HttpSession session){
        User user;
        //请求转发，会话管理
        try{
            user = userService.getUser(username,password);
            if(user.equals(new User(-1,"invalid","ok")))
            {
                return ResponseEntity.status(210).body("Can't find email");
            }
            else if(user.equals(new User(-1,"ok","invalid")))
            {
                return ResponseEntity.status(220).body("Wrong password");
            }
            else
            {
                session.setAttribute("user",user);
                return ResponseEntity.status(200).body(user);
            }
        }catch (RuntimeException e){
            return ResponseEntity.status(400).body("Server Error");
        }
    }


    @GetMapping("/logout")
    public String logout(HttpSession session){
        //注销session（在服务器里删除该session）
        session.invalidate();
        return "Logout successfully";
    }


}
package com.example.j2ee.controller;

import com.example.j2ee.entity.FullUser;
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
@Api(tags = "登录接口")
public class LoginController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "登录")
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
    @GetMapping("/login/{email}/{password}")
    public ResponseEntity login(@PathVariable(value = "email",required = true)String email,
                        @PathVariable(value = "password",required = true)String password,
                        @ApiIgnore HttpSession session){
        User user;
        //请求转发，会话管理
        try{
            user = userService.getUser(email,password);
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


    @ApiOperation("注销登录")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Logout successfully", response = String.class),
    })
    @GetMapping("/logout")
    public String logout(@ApiIgnore HttpSession session){
        //注销session（在服务器里删除该session）
        session.invalidate();
        return "Logout successfully";
    }

    @ApiOperation("注册")
    @ApiImplicitParam(name = "user", value = "用户信息", required = true, dataType = "FullUser", paramType = "body")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Register successfully", response = User.class),
            @ApiResponse(code = 210, message = "Email already exists", response = String.class),
            @ApiResponse(code = 220, message = "Code is incorrect or not match", response = String.class),
            @ApiResponse(code = 400, message = "Server Error"),
    })
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody FullUser fullUser){
        //请求转发，会话管理
        try{
            String ans = userService.submitFullUser(fullUser);
            if(ans == "Email already exists"){
                return ResponseEntity.status(210).body("Email already exists");
            }
            else if(ans == "Code is incorrect or not match"){
                return ResponseEntity.status(220).body("Code is incorrect or not match");
            }
            else{
                return ResponseEntity.status(200).body(ans);
            }

        }catch (RuntimeException e){
            return ResponseEntity.status(400).body("Server Error");
        }
    }

}
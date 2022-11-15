package com.example.j2ee.controller;

import com.example.j2ee.service.UserService;
import com.example.j2ee.service.UserServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

@RestController
public class UserController {

    private UserService userService = new UserServiceImpl();

    @GetMapping("/getUsernames")
    public List<String> getUsernames(HttpSession session){
        //检查是否登录（session是否存在）
        if(session.getAttribute("user") != null) {
            return userService.getUsernames();
        }
        return Collections.singletonList("你是一个大傻瓜！！！");
    }

}
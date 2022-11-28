package com.example.j2ee.controller;

import com.example.j2ee.entity.ActiveCode;
import com.example.j2ee.entity.FullUser;
import com.example.j2ee.service.UserService;
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
import java.util.List;

@RestController
@Tag(name = "用户接口")
public class UserController
{
    private final HttpSession session;
    @Autowired
    private UserService userService;

    public UserController(HttpSession session)
    {
        this.session = session;
    }

    @Operation(summary = "用邮件查询用户信息", description = "用邮件查询用户信息,需要用户登录,session验证")
    @GetMapping("/getUsernames/{email}")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content()),
            })
    public ResponseEntity<FullUser> getFullUserInfo(
            @PathVariable String email)
    {
        if (session.getAttribute("email") != null && session.getAttribute("email").equals(email))
        {
            return ResponseEntity.status(200).body(userService.getFullUser(email));
        }
        else
        {
            return ResponseEntity.status(403).body(null);
        }
    }

    @Operation(summary = "修改用户信息", description = "需要session验证,不能修改姓名,注册码,权限,邮箱,id")
    @PostMapping("/updateUser")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "210", description = "Can't find email", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content()),
                    @ApiResponse(responseCode = "400", description = "Server Error", content = @Content()),
            })
    public ResponseEntity<FullUser> updateFullUserInfo(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "This is a json like data, upload the FullUser", required = true)
                                                       @RequestBody FullUser fullUser)
    {
        try
        {
            if (session.getAttribute("email").equals(fullUser.getEmail()))
            {
                return ResponseEntity.status(403).body(null);
            }
            else
            {
                int result = userService.updateFullUserInfo(fullUser);
                if (result == 0)
                {
                    return ResponseEntity.status(210).body(null);
                }
                else
                {
                    return ResponseEntity.status(200).body(null);
                }
            }
        } catch (Exception e)
        {
            System.out.println(e);
            return ResponseEntity.status(400).body(null);
        }
    }

    @Operation(summary = "增加激活码", description = "只有管理人员才能修改")
    @PostMapping("/addActivationCode")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content()),
            })
    public ResponseEntity<Void> addActivationCode(@Parameter(description = "激活码") @RequestParam String code,
                                                  @Parameter(description = "真实姓名") @RequestParam String name,
                                                  @Parameter(description = "0代表非管理员，1代表管理员") @RequestParam
                                                  String isAdmin)
    {
        if (session.getAttribute("isAdmin") != null && session.getAttribute("isAdmin").equals("1"))
        {
            userService.addActiveCode(code, name, isAdmin);
            return ResponseEntity.status(200).body(null);
        }
        else
        {
            return ResponseEntity.status(403).body(null);
        }
    }

    @Operation(summary = "展示激活码")
    @GetMapping("/showActivationCode")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content()),
            })
    public ResponseEntity<List<ActiveCode>> showActivationCode()
    {
        if (session.getAttribute("isAdmin") != null && session.getAttribute("isAdmin").equals("1"))
        {
            return ResponseEntity.status(200).body(userService.getActiveCode());
        }
        else
        {
            return ResponseEntity.status(403).body(null);
        }
    }
}
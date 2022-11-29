package com.example.j2ee.controller;

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

    @Operation(summary = "用邮件查询用户信息", description = "需要用户自己或者管理员进行查询")
    @GetMapping("/getUsernames/email={email}")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content()),
            })
    public ResponseEntity<FullUser> getFullUserInfo(
            @PathVariable String email)
    {
        if (session.getAttribute("email") == null)
        {
            return ResponseEntity.status(403).body(null);
        }
        else if (session.getAttribute("isAdmin").equals("1"))
        {
            return ResponseEntity.status(200).body(userService.getFullUserByEmail(email));
        }
        else if (session.getAttribute("email").equals(email))
        {
            return ResponseEntity.status(200).body(userService.getFullUserByEmail(email));
        }
        else
        {
            return ResponseEntity.status(403).body(null);
        }
    }

    @Operation(summary = "用姓名查询用户信息", description = "需要用户自己或者管理员进行查询")
    @GetMapping("/getUsernames/name={name}")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content()),
            })
    public ResponseEntity<FullUser> getFullUserInfoByName(
            @PathVariable String name)
    {
        if (session.getAttribute("email") == null)
        {
            return ResponseEntity.status(403).body(null);
        }
        else if (session.getAttribute("isAdmin").equals("1"))
        {
            return ResponseEntity.status(200).body(userService.getFullUserByName(name));
        }
        else if (session.getAttribute("email").equals(userService.getFullUserByName(name).getEmail()))
        {
            return ResponseEntity.status(200).body(userService.getFullUserByName(name));
        }
        else
        {
            return ResponseEntity.status(403).body(null);
        }
    }


    @Operation(summary = "修改用户信息", description = "需要用户或者管理员修改，不要修改注册码,权限,邮箱,id")
    @PostMapping("/updateUser")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "210", description = "Can't find email", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content()),
                    @ApiResponse(responseCode = "400", description = "Server Error", content = @Content()),
            })
    public ResponseEntity<FullUser> updateFullUserInfo(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "这是一个类似JSON的一个类，根据邮箱索引，其中id与isAdmin与Code的值无效，可以随意填写",
            required = true)
                                                       @RequestBody FullUser fullUser)
    {
        try
        {
            if (session.getAttribute("email") == null)
            {
                return ResponseEntity.status(403).body(null);
            }
            else if (session.getAttribute("isAdmin").equals("1"))
            {
                fullUser.setIsAdmin(userService.getFullUserByEmail(fullUser.getEmail()).getIsAdmin());
                fullUser.setId(userService.getFullUserByEmail(fullUser.getEmail()).getId());
                fullUser.setCode(userService.getFullUserByEmail(fullUser.getEmail()).getCode());
                fullUser.setEmail(userService.getFullUserByEmail(fullUser.getEmail()).getEmail());
                int result = userService.updateFullUserInfo(fullUser);
                if (result == 0)
                {
                    return ResponseEntity.status(210).body(null);
                }
                else
                {
                    return ResponseEntity.status(200).body(fullUser);
                }
            }
            else if (session.getAttribute("email").equals(fullUser.getEmail()))
            {
                fullUser.setIsAdmin(userService.getFullUserByEmail(fullUser.getEmail()).getIsAdmin());
                fullUser.setId(userService.getFullUserByEmail(fullUser.getEmail()).getId());
                fullUser.setCode(userService.getFullUserByEmail(fullUser.getEmail()).getCode());
                fullUser.setEmail(userService.getFullUserByEmail(fullUser.getEmail()).getEmail());
                int result = userService.updateFullUserInfo(fullUser);
                if (result == 0)
                {
                    return ResponseEntity.status(210).body(null);
                }
                else
                {
                    return ResponseEntity.status(200).body(fullUser);
                }
            }
            else
            {
                return ResponseEntity.status(403).body(null);
            }
        } catch (Exception e)
        {
            System.out.println(e);
            return ResponseEntity.status(400).body(null);
        }
    }


    @Operation(summary = "显示所有用户", description = "仅管理员可以查询")
    @GetMapping("/showAllUser")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content()),
            })
    public ResponseEntity<List<FullUser>> showAllUser()
    {
        if (session.getAttribute("isAdmin") != null && session.getAttribute("isAdmin").equals("1"))
        {
            return ResponseEntity.status(200).body(userService.getAllUsers());
        }
        else
        {
            return ResponseEntity.status(403).body(null);
        }
    }

    @Operation(summary = "删除用户", description = "仅管理员或用户自己可以删除")
    @DeleteMapping("/deleteUser")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Unauthorized"),
            })
    public ResponseEntity<Void> deleteUser(@Parameter(description = "用户邮箱") @RequestParam String email)
    {
        int rt = -1;
        if (session.getAttribute("isAdmin") != null && session.getAttribute("isAdmin").equals("1"))
        {
            rt = userService.deleteUser(email);
        }
        else if (session.getAttribute("email") != null && session.getAttribute("email").equals(email))
        {
            session.invalidate();
            rt = userService.deleteUser(email);
        }

        if (rt == -1)
        {
            return ResponseEntity.status(403).body(null);
        }
        else if (rt == 0)
        {
            return ResponseEntity.status(404).body(null);
        }
        else
        {
            return ResponseEntity.status(200).body(null);
        }
    }


}

package com.example.j2ee.controller;

import com.example.j2ee.entity.FullUser;
import com.example.j2ee.entity.User;
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
import java.util.Objects;

@Tag(name = "登录接口")
@RestController
public class LoginController
{
    private final HttpSession session;
    @Autowired
    private UserService userService;

    public LoginController(HttpSession session)
    {
        this.session = session;
    }

    @Operation(summary = "登录")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "Login success"),
                    @ApiResponse(responseCode = "210", description = "Can't find email", content = @Content()),
                    @ApiResponse(responseCode = "220", description = "Wrong password", content = @Content()),
                    @ApiResponse(responseCode = "400", description = "Server Error", content = @Content()),
            })
    @GetMapping("/login/{email}/{password}")
    public ResponseEntity<User> login(@PathVariable(value = "email", required = true) String email,
                                      @PathVariable(value = "password", required = true) String password)
    {
        User user;
        //请求转发，会话管理
        try
        {
            user = userService.getUser(email, password);
            if (user.equals(new User(-1, "invalid", "ok")))
            {
                return ResponseEntity.status(210).body(null);
            }
            else if (user.equals(new User(-1, "ok", "invalid")))
            {
                return ResponseEntity.status(220).body(null);
            }
            else
            {
                session.setAttribute("email", user.getEmail());
                session.setAttribute("isAdmin", userService.getFullUserByEmail(user.getEmail()).getIsAdmin());
                return ResponseEntity.status(200).body(user);
            }
        } catch (RuntimeException e)
        {
            return ResponseEntity.status(400).body(null);
        }
    }


    @Operation(summary = "注销登录")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Logout successfully"),})
    @GetMapping("/logout")
    public void logout()
    {
        //注销session（在服务器里删除该session）
        session.invalidate();
    }

    @Operation(summary = "注册")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "Register successfully"),
                    @ApiResponse(responseCode = "210", description = "Email already exists", content = @Content()),
                    @ApiResponse(
                            responseCode = "220", description = "Code is incorrect or not match", content = @Content()),
                    @ApiResponse(responseCode = "400", description = "Server Error", content = @Content()),
            })
    @PutMapping("/register")
    public ResponseEntity<FullUser> register(@Parameter(description = "nick name") @RequestParam String username,
                                             @RequestParam String phone,
                                             @RequestParam String email,
                                             @RequestParam String school,
                                             @RequestParam String password,
                                             @Parameter(description = "activation code") @RequestParam String code,
                                             @Parameter(description = "real name") @RequestParam String name)
    {
        //请求转发，会话管理
        FullUser fullUser = new FullUser(0, username, phone, email, school, password, "0", code, name);
        try
        {
            String ans = userService.submitFullUser(fullUser);
            if (Objects.equals(ans, "Email already exists"))
            {
                return ResponseEntity.status(210).body(null);
            }
            else if (Objects.equals(ans, "Code is incorrect or not match"))
            {
                return ResponseEntity.status(220).body(null);
            }
            else
            {
                FullUser rtFullUser = userService.getFullUserByEmail(fullUser.getEmail());
                return ResponseEntity.status(200).body(rtFullUser);
            }

        } catch (RuntimeException e)
        {
            return ResponseEntity.status(400).body(null);
        }
    }

    @Operation(summary = "忘记密码")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "Reset password successfully"),
                    @ApiResponse(responseCode = "210", description = "Email not exists", content = @Content()),
                    @ApiResponse(
                            responseCode = "220", description = "Code is incorrect or not match", content = @Content()),
                    @ApiResponse(responseCode = "400", description = "Server Error", content = @Content()),
            })
    @PostMapping("/resetPassword")
    public ResponseEntity<Void> resetPassword(
            @RequestParam(value = "email") String email,
            @Parameter(description = "activation code") @RequestParam(value = "code") String code,
            @RequestParam(value = "newPassword") String newPassword)
    {
        //请求转发，会话管理
        try
        {
            String ans = userService.resetPassword(email, newPassword, code);
            if (ans == "Email not exists")
            {
                return ResponseEntity.status(210).body(null);
            }
            else if (ans == "Code is incorrect or not match")
            {
                return ResponseEntity.status(220).body(null);
            }
            else
            {
                return ResponseEntity.status(200).body(null);
            }

        } catch (RuntimeException e)
        {
            return ResponseEntity.status(400).body(null);
        }

    }

}

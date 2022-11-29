package com.example.j2ee.controller;

import com.example.j2ee.entity.ActiveCode;
import com.example.j2ee.service.ActiveCodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@Tag(name = "激活码信息")
public class ActiveCodeController
{
    private final HttpSession session;
    @Autowired
    private ActiveCodeService activeCodeService;

    public ActiveCodeController(HttpSession session)
    {
        this.session = session;

    }

    @Operation(summary = "增加激活码", description = "只有管理人员才能修改")
    @PutMapping("/addActivationCode")
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
            activeCodeService.addActiveCode(code, name, isAdmin);
            return ResponseEntity.status(200).body(null);
        }
        else
        {
            return ResponseEntity.status(403).body(null);
        }
    }

    @Operation(summary = "展示激活码", description = "只有管理人员才能增加")
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
            return ResponseEntity.status(200).body(activeCodeService.getActiveCode());
        }
        else
        {
            return ResponseEntity.status(403).body(null);
        }
    }
}

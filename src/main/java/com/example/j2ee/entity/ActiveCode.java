package com.example.j2ee.entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Code", description = "激活码")
public class ActiveCode
{
    @Schema(name = "code", description = "激活码")
    String code;
    @Schema(name = "name", description = "激活码对应的姓名")
    String name;
    @Schema(name = "isAdmin", description = "是否为管理员")
    String isAdmin;

    public ActiveCode()
    {
    }

    public ActiveCode(String code, String name, String isAdmin)
    {
        this.code = code;
        this.name = name;
        this.isAdmin = isAdmin;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getIsAdmin()
    {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin)
    {
        this.isAdmin = isAdmin;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

}

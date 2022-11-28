package com.example.j2ee.entity;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "FullUser", description = "全部用户信息")
public class FullUser
{
    @Schema(name = "id", description = "数据表自增id")
    public Integer id;
    @Schema(name = "username", description = "用户名")
    public String username;
    @Schema(name = "phone", description = "手机号")
    public String phone;
    @Schema(name = "email", description = "邮箱")
    public String email;
    @Schema(name = "school", description = "学校")
    public String school;
    @Schema(name = "password", description = "密码")
    public String password;
    @Schema(name = "isAdmin", description = "是否为管理员(是为1，否为0)")

    public String isAdmin;
    @Schema(name = "code", description = "激活码")
    public String code;

    @Schema(name = "name", description = "姓名")
    public String name;
    public FullUser() {
    }

    public FullUser(Integer id, String username, String phone, String email, String school, String password, String isAdmin, String code, String name) {
        this.id = id;
        this.username = username;
        this.phone = phone;
        this.email = email;
        this.school = school;
        this.password = password;
        this.isAdmin = isAdmin;
        this.code = code;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

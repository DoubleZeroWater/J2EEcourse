package com.example.j2ee.entity;

import io.swagger.annotations.ApiModel;

@ApiModel("FullUser class")
public class FullUser extends User {
    private Integer id;
    private String username;
    private String phone;
    private String email;
    private String school;
    private String password;
    private String isAdmin;
    private String code;

    private String name;
    public FullUser() {
    }

    public FullUser(Integer id, String username, String phone, String email, String school, String password, String isAdmin, String code) {
        this.id = id;
        this.username = username;
        this.phone = phone;
        this.email = email;
        this.school = school;
        this.password = password;
        this.isAdmin = isAdmin;
        this.code = code;
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

package com.example.j2ee.entity;

import io.swagger.annotations.ApiModel;

@ApiModel("User")
public class User {
    private Integer id;
    private String email;
    private String password;

    public User() {
    }

    public User(Integer id, String email, String password)
    {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        User user = (User) obj;

        if (id != null ? !id.equals(user.id) : user.id != null)
            return false;
        if (email != null ? !email.equals(user.email) : user.email != null)
            return false;
        return password != null ? password.equals(user.password) : user.password == null;
    }
}
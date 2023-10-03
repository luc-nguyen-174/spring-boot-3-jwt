package com.example.securityspringboot3.dto;

import com.example.securityspringboot3.entity.UserInfo;

import java.util.Set;

public class SignUpDTO {
    private String username;
    private String email;
    private String password;
    private Set<String> roles;

    public UserInfo toUserInfo() {
        return new UserInfo(username, email, password);
    }

    public SignUpDTO() {
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}

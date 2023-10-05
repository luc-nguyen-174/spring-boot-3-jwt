package com.example.securityspringboot3.dto;

import com.example.securityspringboot3.entity.UserInfo;
import lombok.Getter;

import java.util.Set;

public class UserDTO {
    private String username;
    private String password;
    private String email;
    private Set<String> roles;

    public UserInfo toUserInfo() {
        return new UserInfo(username, password, email);
    }
    public UserDTO() {
    }

    public UserDTO(String username, String email, String password, Set<String> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
package com.example.securityspringboot3.dto;

import com.example.securityspringboot3.entity.UserInfo;
import lombok.Getter;

@Getter
public class UserDTO {
    private String username;
    private String email;
    private String password;
    private String roles;

    public UserInfo toUserInfo() {
        return new UserInfo(username, password, email, roles);
    }
    public UserDTO() {
    }

    public UserDTO(String username, String email, String password, String roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
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

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
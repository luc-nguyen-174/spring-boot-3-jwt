package com.example.securityspringboot3.service.user;

import com.example.securityspringboot3.entity.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// This class is used to convert UserInfo to UserDetails
public class UserInfoDetails implements UserDetails {

    private final String name;
    private final String password;
    private final Set<GrantedAuthority> authorities;

    // Constructor to convert UserInfo to UserDetails
    // This constructor is used in UserInfoService.java

    public UserInfoDetails(UserInfo userInfo) {
        name = userInfo.getUsername();
        password = userInfo.getPassword();
        authorities = userInfo.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

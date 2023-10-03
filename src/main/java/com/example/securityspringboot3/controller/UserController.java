package com.example.securityspringboot3.controller;

import com.example.securityspringboot3.dto.UserDTO;
import com.example.securityspringboot3.entity.AuthRequest;
import com.example.securityspringboot3.entity.UserInfo;
import com.example.securityspringboot3.service.jwt.JwtService;
import com.example.securityspringboot3.service.user.UserInfoService;
import com.fasterxml.jackson.databind.DatabindException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
  
@SuppressWarnings("ALL")
@RestController
@RequestMapping("/auth")
public class UserController {
  
    @Autowired
    private UserInfoService service;
  
    @Autowired
    private JwtService jwtService;
  
    @Autowired
    private AuthenticationManager authenticationManager;
  
    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    /*----------------------  add new user  ---------------------*/
    @PostMapping("/addNewUser")
    public String addNewUser(@RequestBody UserDTO userDTO) {
        return service.addUser(userDTO);
    }
  
    @GetMapping("/user/userProfile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String userProfile() {
        return "Welcome to User Profile";
    }
  
    @GetMapping("/admin/adminProfile")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }
  
    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }
  
}
package com.example.securityspringboot3.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
  
    private String username;
    private String password;
  
}
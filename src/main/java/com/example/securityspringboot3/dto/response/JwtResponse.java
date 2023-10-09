package com.example.securityspringboot3.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private Long id;
    private String accessToken;
    private String refreshToken;
    private final String type = "Bearer";
    private String name;
    private String email;
    private Collection<? extends GrantedAuthority> authorities;
}

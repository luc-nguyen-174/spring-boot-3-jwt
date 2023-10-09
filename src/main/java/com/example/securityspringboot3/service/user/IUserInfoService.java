package com.example.securityspringboot3.service.user;

import com.example.securityspringboot3.dto.request.AuthRequest;
import com.example.securityspringboot3.entity.UserInfo;
import com.example.securityspringboot3.service.IGenericService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface IUserInfoService extends IGenericService<UserInfo> {
    ResponseEntity<?> confirmEmail(String confirmationToken);

    Optional<UserInfo> findByName(String name);

    ResponseEntity<?> login(AuthRequest authRequest, Authentication authentication);

//    ResponseEntity<?> logout(Authentication authentication, String token);
}

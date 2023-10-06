package com.example.securityspringboot3.service.user;

import com.example.securityspringboot3.entity.UserInfo;
import com.example.securityspringboot3.service.IGenericService;
import org.springframework.http.ResponseEntity;

public interface IUserInfoService extends IGenericService<UserInfo> {
    ResponseEntity<?> confirmEmail(String confirmationToken);
}

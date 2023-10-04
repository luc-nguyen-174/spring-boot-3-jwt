package com.example.securityspringboot3.service.user;

import com.example.securityspringboot3.dto.UserDTO;
import com.example.securityspringboot3.entity.UserInfo;
import com.example.securityspringboot3.service.IServiceGeneral;

public interface IUserInfoService extends IServiceGeneral<UserInfo> {
    Object saveUserFromDTO(UserDTO userDTO);
}

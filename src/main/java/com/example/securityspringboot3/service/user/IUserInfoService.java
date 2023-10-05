package com.example.securityspringboot3.service.user;

import com.example.securityspringboot3.entity.UserInfo;
import com.example.securityspringboot3.service.IGenericService;

public interface IUserInfoService extends IGenericService<UserInfo> {
    void addRoleToUser(Long userId, Long roleId);

}

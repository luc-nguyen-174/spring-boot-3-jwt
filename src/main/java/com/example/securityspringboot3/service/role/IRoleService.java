package com.example.securityspringboot3.service.role;

import com.example.securityspringboot3.entity.Role;
import com.example.securityspringboot3.service.IServiceGeneral;

import java.util.Set;

public interface IRoleService extends IServiceGeneral<Role> {
    Set<Role> getRoleByName(Set<String> name);

}

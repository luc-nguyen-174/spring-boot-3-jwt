package com.example.securityspringboot3.service.role;

import com.example.securityspringboot3.entity.Role;
import com.example.securityspringboot3.service.IGenericService;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public interface IRoleService extends IGenericService<Role> {
    Role findByName(String name);

    Set<Role> getRoleByName(Set<String> name);
}

package com.example.securityspringboot3.service.role;

import com.example.securityspringboot3.entity.Role;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface IRoleService {
    Role findByName(String name);

    Optional<Role> findRoleById(@NotNull Long id);
}

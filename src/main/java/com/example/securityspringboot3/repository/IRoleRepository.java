package com.example.securityspringboot3.repository;

import com.example.securityspringboot3.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}

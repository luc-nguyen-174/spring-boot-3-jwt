package com.example.securityspringboot3.service.role;

import com.example.securityspringboot3.entity.Role;
import com.example.securityspringboot3.repository.IRoleRepository;
import org.hibernate.type.SerializableType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class RoleService implements IRoleService {
    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public Iterable<Role> findAll() {
        return null;
    }

    @Override
    public Optional<Role> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Role save(Role role) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public Set<Role> getRoleByName(Set<String> name) {
        Set<Role> roles = new HashSet<>();
        for (String roleName : name) {
            Role role = roleRepository.findByName(roleName);
            if (role != null) {
                roles.add(role);
            }
        }
        return roles;
    }
}

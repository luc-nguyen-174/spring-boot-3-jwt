package com.example.securityspringboot3.service.role;

import com.example.securityspringboot3.entity.Role;
import com.example.securityspringboot3.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class RoleService implements IRoleService{
    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Set<Role> getRoleByName(Set<String> roleName) {
        Set<Role> roles = new HashSet<>();
        for (String role : roleName) {
            Optional<Role> roleOptional = Optional.ofNullable(findByName(role));
            roleOptional.ifPresent(roles::add);
        }
        return roles;
    }

    @Override
    public Iterable<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void remove(Long id) {
        roleRepository.deleteById(id);
    }
}

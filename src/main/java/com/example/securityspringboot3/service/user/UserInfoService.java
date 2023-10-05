package com.example.securityspringboot3.service.user;

import com.example.securityspringboot3.dto.UserDTO;
import com.example.securityspringboot3.entity.Role;
import com.example.securityspringboot3.entity.UserInfo;
import com.example.securityspringboot3.repository.IRoleRepository;
import com.example.securityspringboot3.repository.UserInfoRepository;
import com.example.securityspringboot3.service.role.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserInfoService implements UserDetailsService, IUserInfoService {

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userDetail = userInfoRepository.findByUsername(username);
        // Converting userDetail to UserDetails
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }

    public String addUser(UserDTO userDTO) {
        if (userInfoRepository.countByUsername(userDTO.getUsername()) != 0) {
            if (userInfoRepository.findByUsernameAndRolesName(userDTO.getUsername(), userDTO.getRoles()).isPresent()) {
                return "User already exists";
            } else {
                //chinh sua lai code de khi dang ki, neu user da ton tai thi se them role moi cho user do
                UserInfo userInfo = userInfoRepository.findByUsername(userDTO.getUsername()).get();
                //new role
                String roles = userDTO.getRoles();
                //get currentRole
                Set<Role> currentRole = userInfo.getRoles();
                //add roles and currentRole to new Set
                currentRole.add(roleService.findByName(roles));
                // add new role to user
                userInfo.setRoles(currentRole);
                userInfoRepository.save(userInfo);
                return "New role added to user";
            }
        } else if (userDTO.getUsername().isEmpty() || userDTO.getPassword().isEmpty()) {
            return "Username or Password cannot be empty";
        } else if (userDTO.getPassword().length() < 8) {
            return "Password must be at least 8 characters";
        } else if (userDTO.getUsername().length() < 4) {
            return "Username must be at least 4 characters";
        } else if (userDTO.getUsername().length() > 20) {
            return "Username must be less than 20 characters";
        } else if (userDTO.getPassword().length() > 20) {
            return "Password must be less than 20 characters";
        } else if (userDTO.getUsername().contains(" ") || userDTO.getPassword().contains(" ")) {
            return "Username or Password cannot contain spaces";
        }
        UserInfo userInfo = userDTO.toUserInfo();
        String roles = userDTO.getRoles();
        userInfo.setEmail(userDTO.getEmail());
        userInfo.setRoles(Set.of(roleService.findByName(roles)));
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        userInfoRepository.save(userInfo);
        return "User Added Successfully";
    }

    @Override
    public Iterable<UserInfo> findAll() {
        return userInfoRepository.findAll();
    }

    @Override
    public Optional<UserInfo> findById(Long id) {
        return userInfoRepository.findById(id);
    }

    //not used
    @Override
    public UserInfo save(UserInfo userInfo) {
        return userInfoRepository.save(userInfo);
    }

    @Override
    public void remove(Long id) {
        userInfoRepository.deleteById(id);
    }

    @Override
    public void addRoleToUser(Long userId, Long roleId) {
        userInfoRepository.addRoleToUser(userId, roleId);
    }
}

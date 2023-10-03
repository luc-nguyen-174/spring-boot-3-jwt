package com.example.securityspringboot3.service.user;

import com.example.securityspringboot3.dto.SignUpDTO;
import com.example.securityspringboot3.entity.Role;
import com.example.securityspringboot3.entity.UserInfo;
import com.example.securityspringboot3.repository.IRoleRepository;
import com.example.securityspringboot3.repository.UserInfoRepository;
import com.example.securityspringboot3.service.role.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserInfoRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private IRoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserInfo> userDetail = repository.findByUsername(username);

        // Converting userDetail to UserDetails
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }

    public String addUser(SignUpDTO signUpDTO) {
        if (repository.findByUsername(signUpDTO.getUsername()).isPresent()) {
            return "User Already Exists";
        }
        if (signUpDTO.getUsername().isEmpty() || signUpDTO.getPassword().isEmpty()) {
            return "Username or Password cannot be empty";
        }
        if (signUpDTO.getRoles().isEmpty()) {
            return "User must have at least one role";
        }
        UserInfo user = signUpDTO.toUserInfo();
        Set<String> roles = signUpDTO.getRoles();
        Set<Role> roleSet = roleService.getRoleByName(roles);
        user.setRoles(roleSet);
        signUpDTO.setPassword(encoder.encode(signUpDTO.getPassword()));
        repository.save(user);
        return "User Added Successfully";
    }
}

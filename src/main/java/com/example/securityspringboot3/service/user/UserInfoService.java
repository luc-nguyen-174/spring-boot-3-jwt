package com.example.securityspringboot3.service.user;

import com.example.securityspringboot3.dto.UserDTO;
import com.example.securityspringboot3.entity.ConfirmationToken;
import com.example.securityspringboot3.entity.Role;
import com.example.securityspringboot3.entity.UserInfo;
import com.example.securityspringboot3.repository.ConfirmationTokenRepository;
import com.example.securityspringboot3.repository.UserInfoRepository;
import com.example.securityspringboot3.service.email.EmailService;
import com.example.securityspringboot3.service.role.IRoleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserInfoService implements UserDetailsService, IUserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private EmailService emailService;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<UserInfo> userDetail = userInfoRepository.findByName(name);

        // Converting userDetail to UserDetails
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + name));
    }

    @Transactional(rollbackOn = Exception.class)
    public String addUser(UserDTO userDTO) {
        if (userInfoRepository.countByUsername(userDTO.getName()) != 0) {
            Optional<UserInfo> userInfoOptional = userInfoRepository.findByName(userDTO.getName());
            if (userInfoOptional.isPresent()
                    && userInfoOptional.get()
                    .getRoles()
                    .stream()
                    .map(Role::getName)
                    .collect(Collectors.toSet())
                    .containsAll(userDTO.getRoles())) {
                return "User already exists";
            } else {
                //chinh sua lai code de khi dang ki, neu user da ton tai thi se them role moi cho user do
                //new role
                Set<String> roles = userDTO.getRoles();
                //get currentRole
                Set<Role> currentRole = userInfoOptional.get().getRoles();
                //add roles and currentRole to new Set
                currentRole.addAll(roles.stream().map(roleService::findByName).collect(Collectors.toSet()));
                // add new role to user
                userInfoOptional.get().setRoles(currentRole);
                save(userInfoOptional.get());
                return "New role added to user";
            }
        } else if (userDTO.getName().isEmpty() || userDTO.getPassword().isEmpty()) {
            return "Username or Password cannot be empty";
        } else if (userDTO.getPassword().length() < 8) {
            return "Password must be at least 8 characters";
        } else if (userDTO.getName().length() < 4) {
            return "Username must be at least 4 characters";
        } else if (userDTO.getName().length() > 20) {
            return "Username must be less than 20 characters";
        } else if (userDTO.getPassword().length() > 20) {
            return "Password must be less than 20 characters";
        } else if (userDTO.getName().contains(" ") || userDTO.getPassword().contains(" ")) {
            return "Username or Password cannot contain spaces";
        } else if (userInfoRepository.existsByEmail(userDTO.getEmail())) {
            return "Email already exists";
        }
        // save user
        UserInfo userInfo = userDTO.toUserInfo();
        Set<String> roles = userDTO.getRoles();
        Set<Role> roleSet = roleService.getRoleByName(roles);
        userInfo.setRoles(roleSet);
        userInfo.setEmail(userDTO.getEmail());
        userInfo.setEnabled(false);
        userInfo.setPassword(encoder.encode(userDTO.getPassword()));
        userInfoRepository.save(userInfo);

        //save token by user to token table
        // one to one
        ConfirmationToken confirmationToken = new ConfirmationToken(userInfo);
        confirmationTokenRepository.save(confirmationToken);

        //send email
        emailService.sendEmail(userInfo, confirmationToken.getConfirmationToken());
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
    //save user by role object
    //not use dto
    @Override
    public UserInfo save(UserInfo userInfo) {
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        return userInfoRepository.save(userInfo);
    }

    @Override
    public void remove(Long id) {
        userInfoRepository.deleteById(id);
    }


    //confirm email and enable user
    @Override
    public ResponseEntity<?> confirmEmail(String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        if (token != null) {
            UserInfo userInfo = userInfoRepository.findByEmailIgnoreCase(token.getUserInfo().getEmail());
            userInfo.setEnabled(true);
            userInfoRepository.save(userInfo);
//            token.setConfirmationToken(null);
            return ResponseEntity.ok("User confirmed successfully");
        }
        return ResponseEntity.ok("User not enabled");
    }
}

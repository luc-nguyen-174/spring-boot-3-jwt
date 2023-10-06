package com.example.securityspringboot3.controller;

import com.example.securityspringboot3.dto.UserDTO;
import com.example.securityspringboot3.entity.AuthRequest;
import com.example.securityspringboot3.entity.UserInfo;
import com.example.securityspringboot3.service.jwt.JwtService;
import com.example.securityspringboot3.service.user.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Tutorial", description = "Tutorial management APIs")
@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    /*----------------------Swagger and test api---------------------*/
    @Operation(
            summary = "Retrieve a Tutorial by Id",
            description = "Get a Tutorial object by specifying its id. The response is Tutorial object with id, title, description and published status.",
            tags = {"tutorials", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = UserInfo.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})

    //non-secure endpoint
    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @RequestMapping(value = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token") String confirmationToken) {
        return userInfoService.confirmEmail(confirmationToken);
    }


    /*----------------------  add new user  ---------------------*/
    @PostMapping({"/"})
    public String addNewUser(@RequestBody UserDTO userDTO) {
        return userInfoService.addUser(userDTO);
    }

//    @PostMapping("/addNewUser")
//    public ResponseEntity<UserInfo> addNewUser(@RequestBody UserInfo user) {
//        UserInfo userInfo = userInfoService.save(user);
//        return new ResponseEntity<>(userInfo, HttpStatus.OK);
//    }

    /*----------------------  user profile  ---------------------*/
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public String userProfile() {
        return "Welcome to User Profile";
    }

    /*----------------------  admin profile  ---------------------*/
    @GetMapping("/admin")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }


    @GetMapping("/get-all-user")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Iterable<UserInfo>> getAllUser() {
        Iterable<UserInfo> userInfos = userInfoService.findAll();
        return new ResponseEntity<>(userInfos, HttpStatus.OK);
    }

    /*----------------------gen token---------------------*/
    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

}
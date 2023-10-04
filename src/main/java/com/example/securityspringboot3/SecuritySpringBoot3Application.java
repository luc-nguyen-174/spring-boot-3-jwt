package com.example.securityspringboot3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SecuritySpringBoot3Application {

    public static void main(String[] args) {
        SpringApplication.run(SecuritySpringBoot3Application.class, args);
    }

}

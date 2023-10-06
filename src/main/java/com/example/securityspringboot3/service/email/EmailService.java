package com.example.securityspringboot3.service.email;

import com.example.securityspringboot3.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender emailSender;

    @Value("${server.port}")
    private String port;

    @Async
    public void sendEmail(UserInfo userInfo, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userInfo.getEmail());
        message.setSubject("Complete Registration!");
        message.setText("To confirm your account, please click here : "
                + "http://localhost:" + port +
                "/auth/confirm-account?token=" + token);
        emailSender.send(message);
    }
}

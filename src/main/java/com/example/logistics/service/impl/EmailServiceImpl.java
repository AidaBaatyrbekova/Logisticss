package com.example.logistics.service.impl;

import com.example.logistics.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl  implements EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Override
    public void sendVerificationCode(String to, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Verification Code");
        message.setText("Your 4-digit verification code: " + code);
        mailSender.send(message);
    }
}

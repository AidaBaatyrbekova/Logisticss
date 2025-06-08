package com.example.logistics.service;

public interface EmailService {
    void sendVerificationCode(String to, String code);
}

package com.example.logistics.validation;

import com.example.logistics.dto.request.AuthRequest;
import com.example.logistics.dto.request.UserRegisterRequest;
import com.example.logistics.dto.response.AuthResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    AuthResponse register(UserRegisterRequest request);

    AuthResponse login(AuthRequest request);
}
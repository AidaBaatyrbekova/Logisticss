package com.example.logistics.validation;

import com.example.logistics.dto.request.AuthRequest;
import com.example.logistics.dto.request.PasswordResetRequest;
import com.example.logistics.dto.request.UserRegisterRequest;
import com.example.logistics.dto.response.AuthResponse;
import com.example.logistics.dto.response.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserResponse createUser(UserRegisterRequest request);

    AuthResponse login(AuthRequest request);

    ResponseEntity<String> resetPassword(PasswordResetRequest request);
}
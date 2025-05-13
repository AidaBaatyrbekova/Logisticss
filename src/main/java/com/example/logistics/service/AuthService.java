package com.example.logistics.service;

import com.example.logistics.dto.request.SignInRequest;
import com.example.logistics.dto.request.SignUpRequest;
import com.example.logistics.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse signUp(SignUpRequest userRequest);
    AuthResponse signIn(SignInRequest signInRequest);

}
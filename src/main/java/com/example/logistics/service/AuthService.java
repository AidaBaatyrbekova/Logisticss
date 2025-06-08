package com.example.logistics.service;

import com.example.logistics.dto.request.SingInRequest;
import com.example.logistics.dto.request.SingUpRequest;
import com.example.logistics.dto.response.AuthResponse;
import com.example.logistics.dto.response.SimpleResponse;

public interface AuthService {
    SimpleResponse signUp(SingUpRequest userRequest);
    AuthResponse signIn(SingInRequest signInRequest);
    AuthResponse confirmSignUp(String code);
}
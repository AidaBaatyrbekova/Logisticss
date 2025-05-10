package com.example.logistics.service;

import com.example.logistics.dto.request.SignInRequest;
import com.example.logistics.dto.request.SingUpRequest;
import com.example.logistics.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse signUp(SingUpRequest userRequest);
    AuthResponse signIn(SignInRequest signInRequest);

}
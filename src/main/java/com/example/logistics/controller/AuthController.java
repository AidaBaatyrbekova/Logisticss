package com.example.logistics.controller;

import com.example.logistics.dto.request.SignInRequest;
import com.example.logistics.dto.request.SingUpRequest;
import com.example.logistics.dto.response.AuthResponse;
import com.example.logistics.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 🟢 POST: Sign Up
    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> signUp(@RequestBody SingUpRequest request) {
        AuthResponse response = authService.signUp(request);
        return ResponseEntity.ok(response);
    }

    // 🟢 POST: Sign In
    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponse> signIn(@RequestBody SignInRequest request) {
        AuthResponse response = authService.signIn(request);
        return ResponseEntity.ok(response);
    }
}
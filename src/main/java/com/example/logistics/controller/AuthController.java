package com.example.logistics.controller;

import com.example.logistics.dto.request.SingInRequest;
import com.example.logistics.dto.request.SingUpRequest;
import com.example.logistics.dto.response.AuthResponse;
import com.example.logistics.dto.response.SimpleResponse;
import com.example.logistics.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signUp")
    public SimpleResponse singUp(@RequestBody SingUpRequest userRequest) {
        return authService.signUp(userRequest);
    }
    @PostMapping("/signIn")
    public AuthResponse signIn(@RequestBody SingInRequest userRequest) {
        return authService.signIn(userRequest);
    }
    @PostMapping("/verify")
    public AuthResponse verify( @RequestParam String code){
        return authService.confirmSignUp(code);}
}
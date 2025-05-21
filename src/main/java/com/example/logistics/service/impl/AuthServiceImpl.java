package com.example.logistics.service.impl;

import com.example.logistics.dto.request.SignInRequest;
import com.example.logistics.dto.request.SignUpRequest;
import com.example.logistics.dto.response.AuthResponse;
import com.example.logistics.entity.User;
import com.example.logistics.exception.AlreadyExistsException;
import com.example.logistics.exception.BadCredentialForbiddenException;
import com.example.logistics.exception.NotFoundException;
import com.example.logistics.exception.ValidationException;
import com.example.logistics.repository.UserRepository;
import com.example.logistics.security.JwtService;
import com.example.logistics.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthResponse signUp(SignUpRequest request) {
        if (userRepository.existsByPhoneNumber(request.phoneNumber())) {
            throw new AlreadyExistsException("Phone number already in use");
        }
        if (!request.phoneNumber().startsWith("+996")) {
            throw new ValidationException("Phone number must start with +996");
        }
        User user = User.builder()
                .name(request.name())
                .message("User registered successfully")
                .email(request.email())
                .role(request.role())
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .message("User registered successfully")
                .email(user.getEmail())
                .token(jwtService.generateToken(user))
                .role(request.role())
                .build();
    }
    @Override
    public AuthResponse signIn(SignInRequest request) {
        User user = userRepository.findUserByPhoneNumber(request.phoneNumber())
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BadCredentialForbiddenException("Incorrect password");
        }
        return AuthResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .message("User signed in successfully")
                .email(user.getEmail())
                .token(jwtService.generateToken(user))
                .role(user.getRole())
                .build();
    }
}
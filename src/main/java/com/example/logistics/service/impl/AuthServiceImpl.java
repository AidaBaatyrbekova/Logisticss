package com.example.logistics.service.impl;

import com.example.logistics.dto.request.SingInRequest;
import com.example.logistics.dto.request.SingUpRequest;
import com.example.logistics.dto.response.AuthResponse;
import com.example.logistics.dto.response.SimpleResponse;
import com.example.logistics.entity.Role;
import com.example.logistics.entity.TempUser;
import com.example.logistics.entity.User;
import com.example.logistics.exception.AlreadyExistsException;
import com.example.logistics.exception.BadCredentialForbiddenException;
import com.example.logistics.exception.NotFoundException;
import com.example.logistics.repository.UserRepository;
import com.example.logistics.security.JwtService;
import com.example.logistics.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.example.logistics.entity.Role.ADMIN;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final EmailServiceImpl emailService;
    private final Map<String, TempUser> tempUsersByCode = new ConcurrentHashMap<>();

    @Override
    public SimpleResponse signUp(SingUpRequest userRequest) {
        if (userRepository.existsUserByEmail(userRequest.email())) {
            throw new AlreadyExistsException("Email already in use");
        }
        if (!userRequest.password().equals(userRequest.repeatPassword())) {
            throw new RuntimeException("Passwords do not match");
        }
        String code = String.valueOf((int) (Math.random() * 9000) + 1000);

        TempUser tempUser = new TempUser();
        tempUser.setUserName(userRequest.userName());
        tempUser.setLastName(userRequest.lastName());
        tempUser.setEmail(userRequest.email());
        tempUser.setPassword(passwordEncoder.encode(userRequest.password()));
        tempUser.setVerificationCode(code);
        tempUser.setRole(userRequest.role());

        tempUsersByCode.put(code, tempUser);
        emailService.sendVerificationCode(userRequest.email(), code);

        return SimpleResponse.builder()
                .message("Verification code sent to email")
                .status(HttpStatus.OK)
                .build();
    }
    @Override
    public AuthResponse signIn(SingInRequest signInRequest) {
        User user = userRepository.findUserByEmail(signInRequest.email())
                .orElseThrow(() -> {
                    log.info("User with email {} not found", signInRequest.email());
                    return new NotFoundException(String.format("User with email %s not found", signInRequest.email()));
                });

        if (!passwordEncoder.matches(signInRequest.password(), user.getPassword())) {
            throw new BadCredentialForbiddenException("Password incorrect");
        }

        String token = jwtService.generateToken(user);
        return AuthResponse.builder()
                .id(user.getId())
                .role(user.getRole())
                .token(token)
                .build();
    }

    @Override
    public AuthResponse confirmSignUp(String code) {
        TempUser tempUser = tempUsersByCode.get(code);
        if (tempUser == null) {
            throw new BadCredentialForbiddenException("Invalid or expired verification code.");
        }

        User user = User.builder()
                .userName(tempUser.getUserName())
                .lastName(tempUser.getLastName())
                .email(tempUser.getEmail())
                .password(tempUser.getPassword())
                .role(Role.ADMIN)
                .verified(true)
                .build();

        userRepository.save(user);
        tempUsersByCode.remove(code);

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .id(user.getId())
                .role(user.getRole())
                .token(token)
                .build();
    }}
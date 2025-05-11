package com.example.logistics.service.impl;
import com.example.logistics.dto.request.SignInRequest;
import com.example.logistics.dto.request.SingUpRequest;
import com.example.logistics.dto.response.AuthResponse;
import com.example.logistics.entity.Role;
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
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public AuthResponse signUp(SingUpRequest userRequest) {
        if (userRepository.existsByPhoneNumber(userRequest.phoneNumber())) {
            throw new AlreadyExistsException("Phone number already in use");
        }

        if (!userRequest.password().equals(userRequest.repeatPassword())) {
            throw new BadCredentialForbiddenException("Passwords do not match");
        }

        if (!userRequest.phoneNumber().startsWith("+996")) {
            throw new ValidationException("Phone number must start with +996");
        }

        User user = User.builder()
                .name(userRequest.userName())
                .lastName(userRequest.lastName())
                .phoneNumber(userRequest.phoneNumber())
                .password(passwordEncoder.encode(userRequest.password()))
                .role(Role.USER)
                .build();

        userRepository.save(user);
        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .role(user.getRole())
                .token(token)
                .build();
}

    @Override
    public AuthResponse signIn(SignInRequest signInRequest) {
        User user = (User) userRepository.findUserByPhoneNumber(signInRequest.phoneNumber())
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (!passwordEncoder.matches(signInRequest.password(), user.getPassword())) {
            throw new BadCredentialForbiddenException("Password is incorrect");
        }
        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .role(user.getRole())
                .token(token)
                .build();
    }
}
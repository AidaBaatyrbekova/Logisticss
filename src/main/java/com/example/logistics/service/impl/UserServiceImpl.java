package com.example.logistics.service.impl;
import com.example.logistics.dto.request.UserRequest;
import com.example.logistics.dto.response.UserResponse;
import com.example.logistics.entity.User;
import com.example.logistics.exception.NotFoundException;
import com.example.logistics.repository.UserRepository;
import com.example.logistics.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse saveUser(UserRequest request) {
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new RuntimeException("Phone number already exists");
        }

        User user = User.builder()
                .name(request.getName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        return UserResponse.builder()
                .message("User saved successfully")
                .status(HttpStatus.CREATED)
                .name(user.getName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .build();
    }

    @Override
    public UserResponse updateUser(UserRequest userRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String phoneNumber = authentication.getName();

        User user = (User) userRepository.findUserByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NotFoundException("User not found"));

        user.setName(userRequest.getName());
        user.setLastName(userRequest.getLastName());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setEmail(userRequest.getEmail());

        userRepository.save(user);

        return UserResponse.builder()
                .message("User updated successfully")
                .status(HttpStatus.OK)
                .name(user.getName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .build();
    }

    @Override
    public UserResponse deleteUser(String userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String phoneNumber = authentication.getName();

        User user = (User) userRepository.findUserByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NotFoundException("User not found"));

        userRepository.delete(user);

        return UserResponse.builder()
                .message("User deleted successfully")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public UserResponse getUser(String userId) {
        User user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new NotFoundException("User not found"));

        return UserResponse.builder()
                .name(user.getName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }
}
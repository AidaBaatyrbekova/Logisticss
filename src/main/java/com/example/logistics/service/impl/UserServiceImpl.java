package com.example.logistics.service.impl;

import com.example.logistics.dto.request.UserRequest;
import com.example.logistics.dto.response.UserResponse;
import com.example.logistics.entity.Role;
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
import java.util.stream.Collectors;

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
                .phoneNumber(request.getPhoneNumber())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole() == null ? Role.USER : request.getRole())
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

        User user = userRepository.findUserByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NotFoundException("User not found"));
        user.setName(userRequest.getName());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setEmail(userRequest.getEmail());
        user.setLastName(userRequest.getLastName());
        user.setRole(userRequest.getRole());
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
    public UserResponse deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with ID " + userId + " not found"));

        userRepository.delete(user);

        return UserResponse.builder()
                .message("User deleted successfully")
                .status(HttpStatus.OK)
                .build();

    }
    @Override
    public UserResponse getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with ID " + userId + " not found"));

        return UserResponse.builder()
                .name(user.getName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .status(HttpStatus.OK)
                .message("User retrieved successfully")
                .build();
    }

    @Override
    public List<UserResponse> getUsersSortedByName() {
        List<User> users = userRepository.findAllByOrderByNameAsc();
        return users.stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> getUsersSortedByRole() {
        return userRepository.findAllByOrderByRoleAsc()
                .stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .name(user.getName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .status(HttpStatus.OK)
                .build();
    }
}
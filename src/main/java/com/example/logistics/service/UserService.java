package com.example.logistics.service;

import com.example.logistics.dto.request.UserRequest;
import com.example.logistics.dto.response.UserResponse;
import com.example.logistics.entity.User;

import java.util.List;

public interface UserService {
    UserResponse saveUser(UserRequest request);
    UserResponse updateUser(UserRequest userRequest);
    UserResponse deleteUser(String userId);
    UserResponse getUser(String userId);
}
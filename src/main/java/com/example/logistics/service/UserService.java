package com.example.logistics.service;

import com.example.logistics.dto.request.UserRequest;
import com.example.logistics.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse saveUser(UserRequest request);

    UserResponse updateUser(UserRequest request);

    UserResponse deleteUser(Long userId);

    UserResponse getUser(Long userId);

    List<UserResponse> getUsersSortedByName();

    List<UserResponse> getUsersSortedByRole();
}
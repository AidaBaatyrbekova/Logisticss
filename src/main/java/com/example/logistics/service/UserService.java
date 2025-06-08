package com.example.logistics.service;

import com.example.logistics.dto.request.UserRequest;
import com.example.logistics.dto.response.SimpleResponse;
import com.example.logistics.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    SimpleResponse updateUser(UserRequest userRequest);
    SimpleResponse deleteUser();
    List<UserResponse> getAllUsersAsc();
}
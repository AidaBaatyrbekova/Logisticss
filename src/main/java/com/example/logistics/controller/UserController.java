package com.example.logistics.controller;

import com.example.logistics.dto.request.UserRequest;
import com.example.logistics.dto.response.UserResponse;
import com.example.logistics.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Create user
    @PostMapping("/register")
    public ResponseEntity<UserResponse> saveUser(@RequestBody UserRequest userRequest) {
        UserResponse response = userService.saveUser(userRequest);
        return new ResponseEntity<>(response, response.getStatus());
    }

    // Update user (auth required)
    @PutMapping("/update")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest userRequest) {
        UserResponse response = userService.updateUser(userRequest);
        return new ResponseEntity<>(response, response.getStatus());
    }

    // Delete user (auth required)
    @DeleteMapping("/delete")
    public ResponseEntity<UserResponse> deleteUser(@RequestParam String userId) {
        UserResponse response = userService.deleteUser(userId);
        return new ResponseEntity<>(response, response.getStatus());
    }

    // Get user by id
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String userId) {
        UserResponse response = userService.getUser(userId);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
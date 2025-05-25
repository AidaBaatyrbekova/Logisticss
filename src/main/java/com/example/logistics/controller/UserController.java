package com.example.logistics.controller;

import com.example.logistics.dto.request.UserRequest;
import com.example.logistics.dto.response.UserResponse;
import com.example.logistics.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> saveUser(@Valid @RequestBody UserRequest userRequest) {
        UserResponse response = userService.saveUser(userRequest);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PutMapping("/update")
    public ResponseEntity<UserResponse> updateUser(@Valid @RequestBody UserRequest userRequest) {
        UserResponse response = userService.updateUser(userRequest);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable Long userId) {
        UserResponse response = userService.deleteUser(userId);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long userId) {
        UserResponse response = userService.getUser(userId);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/sorted-by-name")
    public ResponseEntity<List<UserResponse>> getUsersSortedByName() {
        List<UserResponse> users = userService.getUsersSortedByName();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/sorted-by-role")
    public ResponseEntity<List<UserResponse>> getUsersSortedByRole() {
        List<UserResponse> users = userService.getUsersSortedByRole();
        return ResponseEntity.ok(users);
    }
}
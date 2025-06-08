package com.example.logistics.controller;

import com.example.logistics.dto.request.UserRequest;
import com.example.logistics.dto.response.SimpleResponse;
import com.example.logistics.dto.response.UserResponse;
import com.example.logistics.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/update")
    public SimpleResponse updateUser(@RequestBody UserRequest userRequest) {
        return userService.updateUser(userRequest);
    }

    @DeleteMapping("/delete")
    public SimpleResponse deleteUser() {
        return userService.deleteUser();
    }

    @GetMapping ("/getAllUsersAsc")
    public List<UserResponse> getAllUsers(){
        return userService.getAllUsersAsc();
    }


}

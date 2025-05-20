package com.example.logistics.dto.request;

import com.example.logistics.entity.Role;
import lombok.Builder;

@Builder
public record SignUpRequest(
        String name,
        String lastName,
        String phoneNumber,
        String password,
        String repeatPassword,
        String  message,
        String email,
        Role role
) {
}
package com.example.logistics.dto.request;

import com.example.logistics.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignUpRequest(
        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters")
        String name,
        @NotBlank(message = "Phone number is required")
        @Size(min = 8, max = 15, message = "Phone number must be valid")
        String phoneNumber,
        @NotBlank(message = "LastName is required")
        String lastName,
        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
        String password,
        String message,
        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        String email,
        Role role
) {
}
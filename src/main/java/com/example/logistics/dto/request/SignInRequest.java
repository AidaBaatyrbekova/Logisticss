package com.example.logistics.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignInRequest(

        @NotBlank(message = "Phone number is required")
        @Size(min = 8, max = 15, message = "Phone number must be valid")
        String phoneNumber,

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
        String password
) {}

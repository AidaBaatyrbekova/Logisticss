package com.example.logistics.dto.request;

import lombok.Builder;

@Builder
public record SignInRequest(
        String phoneNumber,
        String password
) {}

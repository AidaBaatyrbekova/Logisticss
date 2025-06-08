package com.example.logistics.dto.response;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class UserResponse {
    private String username;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String imageUrl;
}
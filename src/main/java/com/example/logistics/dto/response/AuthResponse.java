package com.example.logistics.dto.response;

import com.example.logistics.entity.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthResponse {
    private long id;
    private String name;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String message;
    private String token;
    private Role role;
}
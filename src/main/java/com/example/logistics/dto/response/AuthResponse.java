package com.example.logistics.dto.response;

import com.example.logistics.entity.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthResponse {
    private String token;
    private Role role;
}

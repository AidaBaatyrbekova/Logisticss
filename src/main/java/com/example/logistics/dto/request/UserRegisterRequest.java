package com.example.logistics.dto.request;

import com.example.logistics.entity.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {
    private String fullName;
    private String email;
    private String password;
    private Role role;
    private String phoneNumber;
}
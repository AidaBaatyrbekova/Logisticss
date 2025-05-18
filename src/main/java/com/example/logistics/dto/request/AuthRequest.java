package com.example.logistics.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {
    private String phoneNumber;
    private String password;
}
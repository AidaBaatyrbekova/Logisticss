package com.example.logistics.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetRequest {
    private String phoneNumber;
}
package com.example.logistics.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class UserResponse {
    private String name;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String message;
    private HttpStatus status;
}
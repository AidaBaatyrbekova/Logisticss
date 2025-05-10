package com.example.logistics.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private String name;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
}
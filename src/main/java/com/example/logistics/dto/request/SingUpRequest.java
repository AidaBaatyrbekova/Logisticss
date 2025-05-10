package com.example.logistics.dto.request;

import lombok.Builder;

@Builder
public record SingUpRequest (
        String userName,
        String lastName,
        String phoneNumber,
        String password,
        String repeatPassword
){
}
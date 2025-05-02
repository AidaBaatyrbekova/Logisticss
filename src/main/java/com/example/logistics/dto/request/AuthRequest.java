package com.example.logistics.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthRequest {
    String email;
    String password;
}
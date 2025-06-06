package com.example.logistics.dto.request;

import com.example.logistics.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

    @NotEmpty(message = "Name should not be empty!")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email must not be blank")
    private String email;

    private String lastName;

    @NotBlank(message = "Password is mandatory!")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters!")
    private String password;

    @NotBlank(message = "Phone number should not be empty!")
    @Size(min = 6, max = 30, message = "Phone number must be between 6 and 30 characters!")
    private String phoneNumber;

    private Role role;
}

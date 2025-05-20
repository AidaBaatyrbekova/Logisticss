package com.example.logistics.validation;
import com.example.logistics.dto.request.AuthRequest;
import com.example.logistics.dto.request.UserRequest;
import com.example.logistics.dto.response.AuthResponse;
import com.example.logistics.dto.response.UserResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserResponse createUser(UserRequest request);

    AuthResponse login(AuthRequest request);

}
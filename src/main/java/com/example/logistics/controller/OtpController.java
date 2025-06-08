package com.example.logistics.controller;

import com.example.logistics.dto.request.OtpRequest;
import com.example.logistics.dto.response.OtpResponse;
import com.example.logistics.service.impl.OtpService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otp")
public class OtpController {

    private final OtpService otpService;

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }
    // 1. OTP жөнөтүү
    @PostMapping("/send")
    public ResponseEntity<OtpResponse> sendOtp(@RequestBody @Valid OtpRequest request) {
        otpService.sendOtp(request.getEmail());
        return ResponseEntity.ok(new OtpResponse("OTP ийгиликтүү жөнөтүлдү", true));
    }
    // 2. OTP текшерүү
    @PostMapping("/validate")
    public ResponseEntity<OtpResponse> validateOtp(@RequestBody @Valid OtpRequest request) {
        boolean isValid = otpService.validateOtp(request.getEmail(), request.getOtp());

        if (isValid) {
            return ResponseEntity.ok(new OtpResponse("OTP туура!", true));
        } else {
            return ResponseEntity.status(401)  // Unauthorized
                    .body(new OtpResponse("OTP туура эмес же мөөнөтү өтүп кеткен", false));
        }
    }
}
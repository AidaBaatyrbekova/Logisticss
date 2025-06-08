package com.example.logistics.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class OtpRequest {

    @NotBlank(message = "Email бош болбошу керек")
    @Email(message = "Email туура эмес форматта")
    private String email;

    private String otp; // send үчүн керек эмес, бирок validate үчүн керек

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
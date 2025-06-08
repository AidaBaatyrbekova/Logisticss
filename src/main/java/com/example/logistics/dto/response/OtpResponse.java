package com.example.logistics.dto.response;

public class OtpResponse {

    private String message;
    private boolean success;

    public OtpResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
}
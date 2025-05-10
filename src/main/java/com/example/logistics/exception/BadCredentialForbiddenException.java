package com.example.logistics.exception;

public class BadCredentialForbiddenException extends RuntimeException {
    public BadCredentialForbiddenException(String message) {
        super(message);
    }
}
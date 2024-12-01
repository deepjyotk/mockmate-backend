package com.mockmate.auth_service.exception.custom;

// ValidationException.java
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
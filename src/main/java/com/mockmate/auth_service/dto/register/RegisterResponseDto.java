package com.mockmate.auth_service.dto.register;

public class RegisterResponseDto {
    private String token;
    private String message;
    private String username;
    private String emailId;

    // Constructor
    public RegisterResponseDto(String token, String message, String username, String emailId) {
        this.token = token;
        this.message = message;
        this.username = username;
        this.emailId = emailId;
    }

    // Getters
    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }

    public String getEmailId() {
        return emailId;
    }
}

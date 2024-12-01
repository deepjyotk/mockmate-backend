package com.mockmate.auth_service.dto.login;

public class LoginResponseDto {

    private String token;
    private String message;  // A success message
    private String username; // The username of the logged-in user

    // Constructor
    public LoginResponseDto(String token, String message, String username) {
        this.token = token;
        this.message = message;
        this.username = username;
    }

    public LoginResponseDto() {
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Equals, Hashcode, and toString methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoginResponseDto that = (LoginResponseDto) o;

        if (!token.equals(that.token)) return false;
        if (!message.equals(that.message)) return false;
        return username.equals(that.username);
    }

    @Override
    public int hashCode() {
        int result = token.hashCode();
        result = 31 * result + message.hashCode();
        result = 31 * result + username.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "LoginResponse(token=" + this.token + ", message=" + this.message + ", username=" + this.username + ")";
    }
}

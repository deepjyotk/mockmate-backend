package com.mockmate.auth_service.dto.login;


import com.mockmate.auth_service.validation.annotation.ValidPassword;
import jakarta.validation.constraints.NotNull;

public class LoginRequestDto {

    @NotNull
    private String userOrEmail;  // Can be either username or email

    @NotNull
    @ValidPassword
    private String password;

    // Constructors
    public LoginRequestDto(String userOrEmail, String password) {
        this.userOrEmail = userOrEmail;
        this.password = password;
    }

    public LoginRequestDto() {
    }

    // Getters and Setters
    public String getUserOrEmail() {
        return userOrEmail;
    }

    public void setUserOrEmail(String userOrEmail) {
        this.userOrEmail = userOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Equals, HashCode, and ToString methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoginRequestDto that = (LoginRequestDto) o;

        if (!userOrEmail.equals(that.userOrEmail)) return false;
        return password.equals(that.password);
    }

    @Override
    public int hashCode() {
        int result = userOrEmail.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }


    @Override
    public String toString() {
        return "LoginRequestDto(userOrEmail=" + this.userOrEmail + ", password=" + this.password + ")";
    }
}

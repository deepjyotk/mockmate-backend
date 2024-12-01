package com.mockmate.auth_service.dto.register;

import com.mockmate.auth_service.validation.annotation.ValidEmail;
import com.mockmate.auth_service.validation.annotation.ValidPassword;
import com.mockmate.auth_service.validation.annotation.ValidRole;
import com.mockmate.auth_service.validation.annotation.ValidRolesTargeting;
import jakarta.validation.constraints.*;

import java.util.List;

public class RegisterRequestDto {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Email is required")
    @ValidEmail(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @ValidPassword
    private String password;

    @NotNull(message = "Role is required")
    @ValidRole
    private String role; // Use Enum Role here

    @NotBlank(message = "University major is required")
    private String universityMajor; // Enum values can be added later for validation

    @NotBlank(message = "University name is required")
    private String universityName;

    @ValidRolesTargeting
    @NotEmpty(message = "At least one role targeting is required")
    private List<String> rolesTargeting;

    @Min(value = 0, message = "Relevant work experience must be 0 or greater")
    private int relevantWorkExperience;

    public RegisterRequestDto(String username, String email, String password, String role, String universityMajor,
                              String universityName, List<String> rolesTargeting, int relevantWorkExperience) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.universityMajor = universityMajor;
        this.universityName = universityName;
        this.rolesTargeting = rolesTargeting;
        this.relevantWorkExperience = relevantWorkExperience;
    }

    public RegisterRequestDto() {

    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUniversityMajor() {
        return universityMajor;
    }

    public void setUniversityMajor(String universityMajor) {
        this.universityMajor = universityMajor;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public List<String> getRolesTargeting() {
        return rolesTargeting;
    }

    public void setRolesTargeting(List<String> rolesTargeting) {
        this.rolesTargeting = rolesTargeting;
    }

    public int getRelevantWorkExperience() {
        return relevantWorkExperience;
    }

    public void setRelevantWorkExperience(int relevantWorkExperience) {
        this.relevantWorkExperience = relevantWorkExperience;
    }

    @Override
    public String toString() {
        return "RegisterRequest{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", universityMajor='" + universityMajor + '\'' +
                ", universityName='" + universityName + '\'' +
                ", rolesTargeting=" + rolesTargeting +
                ", relevantWorkExperience=" + relevantWorkExperience +
                '}';
    }
}

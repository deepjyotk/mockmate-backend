package com.mockmate.auth_service.dto.register;

import com.mockmate.auth_service.validation.annotation.ValidEmail;
import com.mockmate.auth_service.validation.annotation.ValidPassword;
import com.mockmate.auth_service.validation.annotation.ValidRole;
import com.mockmate.auth_service.validation.annotation.ValidRolesTargeting;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {

    @NotBlank(message = "OTP is required")
    private String otp;

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
}

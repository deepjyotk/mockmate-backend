package com.mockmate.auth_service.validation.validator;

import com.mockmate.auth_service.validation.annotation.ValidPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private static final String PASSWORD_REGEX  =  "^(?=.*[A-Z]).{4,}$"; // Simplified regex for demonstration

    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        // Initialization if needed
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null || password.isEmpty()) {
            return false; // Consider @NotBlank for non-null check
        }
        return PASSWORD_PATTERN.matcher(password).matches();
    }
}

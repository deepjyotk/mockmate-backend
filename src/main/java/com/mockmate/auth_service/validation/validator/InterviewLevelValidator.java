package com.mockmate.auth_service.validation.validator;
import com.mockmate.auth_service.validation.annotation.ValidRole;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class InterviewLevelValidator implements ConstraintValidator<ValidRole, String> {

    private static final List<String> ALLOWED_LEVELS = Arrays.asList("Beginner", "Intermediate", "Advanced");

    @Override
    public void initialize(ValidRole constraintAnnotation) {
        // No initialization needed in this case
    }

    @Override
    public boolean isValid(String role, ConstraintValidatorContext context) {
        if (role == null || role.isEmpty()) {
            return false; // Use @NotBlank for null or empty checks
        }
        return ALLOWED_LEVELS.contains(role);
    }
}

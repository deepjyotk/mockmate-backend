package com.mockmate.auth_service.validation.validator;

import com.mockmate.auth_service.validation.annotation.ValidRolesTargeting;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class RolesTargetingValidator implements ConstraintValidator<ValidRolesTargeting, List<String>> {

    private static final List<String> ALLOWED_ROLES = Arrays.asList(
            "Software Engineering",
            "Computer Science",
            "Management",
            "Cyber Security",
            "Data Science",
            "Data Analyst"
    );

    @Override
    public boolean isValid(List<String> roles, ConstraintValidatorContext context) {
        if (roles == null || roles.isEmpty()) {
            return false; // @NotEmpty handles this already, but just to be sure
        }

        return roles.stream().allMatch(ALLOWED_ROLES::contains);
    }
}

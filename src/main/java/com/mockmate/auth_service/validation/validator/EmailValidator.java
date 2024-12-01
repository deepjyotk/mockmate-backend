package com.mockmate.auth_service.validation.validator;
import com.mockmate.auth_service.validation.annotation.ValidEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"; // Simplified regex for demonstration

    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    @Override
    public void initialize(ValidEmail constraintAnnotation) {
        // Initialization if needed
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.isEmpty()) {
            return false; // Consider @NotBlank for non-null check
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }
}

package com.mockmate.auth_service.validation.annotation;

import com.mockmate.auth_service.validation.validator.InterviewLevelValidator;
import com.mockmate.auth_service.validation.validator.RolesTargetingValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.List;

@Constraint(validatedBy = InterviewLevelValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidInterviewLevel {
     static final List<String> ALLOWED_LEVELS = Arrays.asList("Beginner", "Intermediate", "Advanced");

    String message() default "Invalid interview level. Allowed values are: Beginner, Intermediate, Advanced";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

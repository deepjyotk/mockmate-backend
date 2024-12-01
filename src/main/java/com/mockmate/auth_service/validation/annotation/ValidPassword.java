package com.mockmate.auth_service.validation.annotation;

import com.mockmate.auth_service.validation.validator.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordValidator.class) // No need for an array unless using multiple validators
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {

    String message() default "Password must be at least 4 characters long and include at least 1 uppercase letter.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

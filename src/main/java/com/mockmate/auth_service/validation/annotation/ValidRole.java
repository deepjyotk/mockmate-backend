package com.mockmate.auth_service.validation.annotation;

import com.mockmate.auth_service.validation.validator.RoleValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = RoleValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidRole {

    String message() default "Invalid role. Allowed roles are FREE_USER, SUBSCRIBED_USER, ADMIN.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

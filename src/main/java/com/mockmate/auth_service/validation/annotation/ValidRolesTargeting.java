package com.mockmate.auth_service.validation.annotation;

import com.mockmate.auth_service.validation.validator.RolesTargetingValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = RolesTargetingValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRolesTargeting {

    String message() default "Invalid rolesTargeting. Allowed values are: [Computer Science, Management, Cyber Security, Data Science, Data Analyst]";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

package com.mockmate.auth_service.validation.annotation;



import com.mockmate.auth_service.validation.validator.TimeSlotValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = TimeSlotValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUTCTimeSlot {
    String message() default "The timeSlotSelected must be in UTC and ISO-8601 format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

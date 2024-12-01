package com.mockmate.auth_service.validation.validator;


import com.mockmate.auth_service.validation.annotation.ValidUTCTimeSlot;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TimeSlotValidator implements ConstraintValidator<ValidUTCTimeSlot, LocalDateTime> {

    @Override
    public boolean isValid(LocalDateTime timeSlot, ConstraintValidatorContext context) {
        if (timeSlot == null) {
            return true; // Let @NotNull handle null checks if required
        }
        try {
            return timeSlot.atZone(ZoneOffset.UTC).getOffset().equals(ZoneOffset.UTC);
        } catch (Exception e) {
            return false;
        }
    }
}

package com.mockmate.auth_service.exception.custom;

public class InvalidOTPException extends RuntimeException{
    private static final String MESSAGE = "OTP Expired or Invalid." ;

    public InvalidOTPException() {
        super(String.format(MESSAGE));
    }


}

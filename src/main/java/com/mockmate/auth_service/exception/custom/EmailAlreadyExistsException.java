package com.mockmate.auth_service.exception.custom;

public class EmailAlreadyExistsException extends RuntimeException {

    private static final String MESSAGE = "Email '%s' already exists" ;
    public EmailAlreadyExistsException(String email) {
        super(String.format(MESSAGE,email));
    }

    public EmailAlreadyExistsException(String email, Throwable cause) {
        super(String.format(MESSAGE,email), cause);
    }
}
package com.mockmate.auth_service.exception.custom;

public class UsernameAlreadyExistsException extends RuntimeException {

    private static final String MESSAGE = "Username '%s' already exists" ;
    public UsernameAlreadyExistsException(String username) {
        super(String.format(MESSAGE,username));
    }

    public UsernameAlreadyExistsException(String username, Throwable cause) {
        super(String.format(MESSAGE,username), cause);
    }
}
package com.mockmate.auth_service.exception.custom;


/**
 * Custom exception to be thrown when no peer has been matched yet for the user.
 */
public class NoPeerMatchedYetException extends RuntimeException {

    public NoPeerMatchedYetException(String message) {
        super(message);
    }

    public NoPeerMatchedYetException(String message, Throwable cause) {
        super(message, cause);
    }
}
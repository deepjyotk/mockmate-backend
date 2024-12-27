package com.mockmate.auth_service.exception.custom;

public class NoQuestionFoundException extends RuntimeException {
    public NoQuestionFoundException(String message) {
        super(message);
    }

    public NoQuestionFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

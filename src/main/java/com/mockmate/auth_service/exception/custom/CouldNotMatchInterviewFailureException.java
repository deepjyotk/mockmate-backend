package com.mockmate.auth_service.exception.custom;

public class CouldNotMatchInterviewFailureException extends RuntimeException {
    public CouldNotMatchInterviewFailureException(String message) {
        super(message);
    }

    public CouldNotMatchInterviewFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}

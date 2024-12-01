package com.mockmate.auth_service.exception.custom;

/**
 * Custom exception to be thrown when a user is waiting for a peer to join.
 */
public class WaitingForPeerException extends RuntimeException {

    public WaitingForPeerException(String message) {
        super(message);
    }

    public WaitingForPeerException(String message, Throwable cause) {
        super(message, cause);
    }
}
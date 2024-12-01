package com.mockmate.auth_service.exception.custom;


/**
 * Custom exception to be thrown when the waiting threshold for a peer is exceeded.
 */
public class PeerWaitingThresholdReachedException extends RuntimeException {

    public PeerWaitingThresholdReachedException(String message) {
        super(message);
    }

    public PeerWaitingThresholdReachedException(String message, Throwable cause) {
        super(message, cause);
    }
}
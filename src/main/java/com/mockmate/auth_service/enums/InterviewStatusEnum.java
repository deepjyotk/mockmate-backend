package com.mockmate.auth_service.enums;

public enum InterviewStatusEnum {
    UPCOMING("UPCOMING"),
    WAITING_TO_JOIN("WAITING_TO_JOIN"),
    IN_PROGRESS("IN_PROGRESS"),
    END("END"),
    DID_NOT_JOIN("DID_NOT_JOIN"),
    INTERRUPTED("INTERRUPTED");

    private final String value;

    InterviewStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static InterviewStatusEnum fromValue(String value) {
        for (InterviewStatusEnum status : InterviewStatusEnum.values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}

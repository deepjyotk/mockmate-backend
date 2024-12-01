package com.mockmate.auth_service.dto.exception;


import java.time.LocalDateTime;
import java.util.Map;

public class ExceptionResponseDto {

    private String requestId;
    private LocalDateTime timestamp;
    private String message;
    private int status;
    private String error;
    private Map<String, String> details;
    private String debugHelperMessage; //! dont show to the client
    private String debugHelperDescription; //! dont show to the client

    public ExceptionResponseDto(String requestId, String message, int status, String error, Map<String, String> details
    ) {
        this.requestId = requestId;
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.status = status;
        this.error = error;
        this.details = details;


    }

    public ExceptionResponseDto(String requestId, String message, int status, String error, Map<String, String> details, String debugHelperMessage, String debugHelperDescription) {
        this.requestId = requestId;
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.status = status;
        this.error = error;
        this.details = details;
        this.debugHelperMessage = debugHelperMessage;
        this.debugHelperDescription = debugHelperDescription;
    }


    public String getRequestId() {
        return this.requestId;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public String getMessage() {
        return this.message;
    }

    public int getStatus() {
        return this.status;
    }

    public String getError() {
        return this.error;
    }

    public Map<String, String> getDetails() {
        return this.details;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setDetails(Map<String, String> details) {
        this.details = details;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ExceptionResponseDto)) return false;
        final ExceptionResponseDto other = (ExceptionResponseDto) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$requestId = this.getRequestId();
        final Object other$requestId = other.getRequestId();
        if (this$requestId == null ? other$requestId != null : !this$requestId.equals(other$requestId)) return false;
        final Object this$timestamp = this.getTimestamp();
        final Object other$timestamp = other.getTimestamp();
        if (this$timestamp == null ? other$timestamp != null : !this$timestamp.equals(other$timestamp)) return false;
        final Object this$message = this.getMessage();
        final Object other$message = other.getMessage();
        if (this$message == null ? other$message != null : !this$message.equals(other$message)) return false;
        if (this.getStatus() != other.getStatus()) return false;
        final Object this$error = this.getError();
        final Object other$error = other.getError();
        if (this$error == null ? other$error != null : !this$error.equals(other$error)) return false;
        final Object this$details = this.getDetails();
        final Object other$details = other.getDetails();
        if (this$details == null ? other$details != null : !this$details.equals(other$details)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ExceptionResponseDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $requestId = this.getRequestId();
        result = result * PRIME + ($requestId == null ? 43 : $requestId.hashCode());
        final Object $timestamp = this.getTimestamp();
        result = result * PRIME + ($timestamp == null ? 43 : $timestamp.hashCode());
        final Object $message = this.getMessage();
        result = result * PRIME + ($message == null ? 43 : $message.hashCode());
        result = result * PRIME + this.getStatus();
        final Object $error = this.getError();
        result = result * PRIME + ($error == null ? 43 : $error.hashCode());
        final Object $details = this.getDetails();
        result = result * PRIME + ($details == null ? 43 : $details.hashCode());
        return result;
    }

    public String toString() {
        return "ExceptionResponseDto(requestId=" + this.getRequestId() + ", timestamp=" + this.getTimestamp() + ", message=" + this.getMessage() + ", status=" + this.getStatus() + ", error=" + this.getError() + ", details=" + this.getDetails() + ")";
    }

    public String getDebugHelperMessage() {
        return this.debugHelperMessage;
    }

    public String getDebugHelperDescription() {
        return this.debugHelperDescription;
    }

    public void setDebugHelperMessage(String debugHelperMessage) {
        this.debugHelperMessage = debugHelperMessage;
    }

    public void setDebugHelperDescription(String debugHelperDescription) {
        this.debugHelperDescription = debugHelperDescription;
    }
}

package com.mockmate.auth_service.dto;

import lombok.Data;
import org.springframework.http.HttpStatusCode;

import java.time.Instant;

@Data
public class ResponseDto<T> {
    private HttpStatusCode statusCode;
    private String message;
    private T payload;
    private String timestamp;

    // Constructor with all fields
    public ResponseDto(T payload, HttpStatusCode statusCode, String message ) {
        this.payload = payload;
        this.statusCode = statusCode ;
        this.message = message != null ? message : "success"; // Default to "success" if null
        this.timestamp = Instant.now().toString();
    }

    public ResponseDto(T payload, HttpStatusCode statusCode ) {
        this(payload, statusCode, "success") ;
    }

    // Getters and setters (optional for your use case)
    public HttpStatusCode getStatus() {
        return statusCode;
    }
}

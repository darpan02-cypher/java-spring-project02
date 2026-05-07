package com.ex.dto;

import java.time.LocalDateTime;

/**
 * Standard error response format for all API errors
 * This ensures consistent error responses across all endpoints
 */
public class ErrorResponse {
    private LocalDateTime timestamp;  // When the error occurred
    private int status;               // HTTP status code (404, 400, 500, etc.)
    private String error;             // Error type/name
    private String message;           // Error message
    private String path;              // API endpoint path where error occurred

    public ErrorResponse(int status, String error, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    // Getters and Setters
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

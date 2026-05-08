package com.ex.dto;

/**
 * Login response DTO - returns JWT token after successful login
 */
public class AuthResponse {
    
    private String token;
    private String email;
    private String message;
    private long expiresIn;  // Token expiration time in milliseconds

    public AuthResponse() {}

    public AuthResponse(String token, String email, String message, long expiresIn) {
        this.token = token;
        this.email = email;
        this.message = message;
        this.expiresIn = expiresIn;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }
}

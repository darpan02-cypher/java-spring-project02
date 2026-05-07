package com.ex.exception;

/**
 * Exception thrown when a student is not found in the database
 */
public class StudentNotFoundException extends RuntimeException {
    
    public StudentNotFoundException(String message) {
        super(message);
    }
    
    public StudentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

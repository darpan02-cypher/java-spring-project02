package com.ex.exception;

/**
 * Exception thrown when student input data is invalid
 */
// This exception can be used to indicate various validation errors, such as missing required fields, invalid email format, etc. that is handled in global exception handler to return a 400 Bad Request response with details about the validation error.
public class InvalidStudentException extends RuntimeException {
    
    public InvalidStudentException(String message) {
        super(message);
    }
    
    public InvalidStudentException(String message, Throwable cause) {
        super(message, cause);
    }
}

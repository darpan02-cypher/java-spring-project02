package com.ex.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.ex.dto.ErrorResponse;

/**
 * Global Exception Handler
 * @RestControllerAdvice = This handles exceptions globally across ALL controllers
 * Whenever any exception is thrown, it gets caught here and returns a formatted error response
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles StudentNotFoundException
     * Returns 404 (Not Found) status code
     */
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleStudentNotFound(
            StudentNotFoundException ex,
            WebRequest request) {
        
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),        // 404
            "Student Not Found",                 // error type
            ex.getMessage(),                     // custom message
            request.getDescription(false).replace("uri=", "")  // the API path
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles InvalidStudentException
     * Returns 400 (Bad Request) status code
     */
    @ExceptionHandler(InvalidStudentException.class)
    public ResponseEntity<ErrorResponse> handleInvalidStudent(
            InvalidStudentException ex,
            WebRequest request) {
        
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),      // 400
            "Invalid Student Data",              // error type
            ex.getMessage(),                     // custom message
            request.getDescription(false).replace("uri=", "")  // the API path
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles DataIntegrityViolationException (Duplicate Email, Unique Constraint Violations)
     * Returns 409 (Conflict) status code
     * Triggered when trying to save duplicate data (e.g., duplicate email)
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(
            DataIntegrityViolationException ex,
            WebRequest request) {
        
        String message = "Duplicate entry detected";
        if (ex.getMessage() != null && ex.getMessage().contains("email")) {
            message = "Email already exists in the system";
        }
        
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.CONFLICT.value(),         // 409
            "Duplicate Entry",                   // error type
            message,                             // custom message
            request.getDescription(false).replace("uri=", "")  // the API path
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    /**
     * Handles MethodArgumentNotValidException (Validation Errors)
     * Returns 400 (Bad Request) status code
     * Triggered when @Valid annotation fails on request body
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex,
            WebRequest request) {
        
        String message = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .findFirst()
            .orElse("Validation failed");
        
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),      // 400
            "Validation Error",                  // error type
            message,                             // custom message with field details
            request.getDescription(false).replace("uri=", "")  // the API path
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles HttpMessageNotReadableException (Malformed JSON)
     * Returns 400 (Bad Request) status code
     * Triggered when client sends invalid JSON format
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            WebRequest request) {
        
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),      // 400
            "Invalid JSON",                      // error type
            "Malformed JSON request body. Please check your JSON syntax.",  // custom message
            request.getDescription(false).replace("uri=", "")  // the API path
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles all other exceptions (catch-all)
     * Returns 500 (Internal Server Error) status code
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
            Exception ex,
            WebRequest request) {
        
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),  // 500
            "Internal Server Error",                    // error type
            "An unexpected error occurred",             // generic message
            request.getDescription(false).replace("uri=", "")  // the API path
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

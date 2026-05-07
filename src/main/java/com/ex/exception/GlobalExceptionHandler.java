package com.ex.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

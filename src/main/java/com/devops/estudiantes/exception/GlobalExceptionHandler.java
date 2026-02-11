package com.devops.estudiantes.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
@ControllerAdvice
public class GlobalExceptionHandler {

   // Handles IllegalArgumentException thrown by our service layer.
   // This exception is thrown when someone tries to register a student with a duplicate ID.
   @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object >> handleIllegalArgumentException(IllegalArgumentException ex) {
       // Create a structured error response
       Map<String, Object> errorResponse = new HashMap<>();
       errorResponse.put("timestamp", LocalDateTime.now());
       errorResponse.put("status", HttpStatus.CONFLICT.value() );
       errorResponse.put("error", "Conflict" );
       errorResponse.put("message", ex.getMessage() );

       // Return 409 conflict with our error details in JSON format
       return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
   }

    // Handles validation errors from @Valid annotation.
    // When the @Valid annotation in the controller finds validation errors
    // (like missing or blank required fields), Spring throws this exception.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", HttpStatus.BAD_REQUEST.value()); // 400
        errorResponse.put("error", "Bad Request");

        // Extract validation errors for each field
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            fieldErrors.put(fieldName, errorMessage);
        });

        errorResponse.put("validationErrors", fieldErrors);
        errorResponse.put("message", "Validation failed for one or more fields");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    // Generic handler for any unexpected exceptions.
    // This is a safety net - if an exception occurs that we haven't specifically
    // handled, this method catches it and returns a generic 500 error.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value()); // 500
        errorResponse.put("error", "Internal Server Error");
        errorResponse.put("message", "An unexpected error occurred");

        // In development, you might want to include ex.getMessage() for debugging
        // In production, avoid exposing internal error details to clients

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}

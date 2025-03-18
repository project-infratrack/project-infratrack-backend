package com.G153.InfratrackUserPortal.Exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for the application.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles validation exceptions.
     *
     * @param ex the MethodArgumentNotValidException
     * @return a map of field errors and their messages
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return errors;
    }

    /**
     * Handles general exceptions.
     *
     * @param ex the Exception
     * @return the exception message
     */
    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex) {
        return ex.getMessage();
    }
}
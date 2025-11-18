package com.psbe.patientservicebe.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);//this allows handling exceptions globally across the whole application, we can use this to hide detailed error messages from clients

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, List<String>> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String field = error.getField();
            String message = error.getDefaultMessage();

            // If this field already has error messages, append it
            errors.computeIfAbsent(field, key -> new ArrayList<>()).add(message);//this retrieves the list of error messages for the field, or creates a new list if none exists, and adds the new message to it
        });

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, List<String>>> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        return getMapResponseEntity(ex.getMessage());
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Map<String, List<String>>> handlePatientNotFoundException(PatientNotFoundException ex) {
        return getMapResponseEntity(ex.getMessage());
    }

    private ResponseEntity<Map<String, List<String>>> getMapResponseEntity(String message) {
        log.error(message);
        Map<String, List<String>> errors = new HashMap<>();
        List<String> messages = new ArrayList<>();
        messages.add(message);
        errors.put("message", messages);
        return ResponseEntity.badRequest().body(errors);
    }

}

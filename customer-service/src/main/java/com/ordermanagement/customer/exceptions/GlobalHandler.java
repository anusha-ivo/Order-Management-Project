package com.ordermanagement.customer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
public class GlobalHandler {

    private static final String SOURCE_APP = "AUTH-IRIS";

    private Map<String, Object> buildError(
            String label,
            HttpStatus status,
            String message,
            String level,
            String severity) {

        return Map.of(
                "label", label,
                "code", String.valueOf(status.value()),
                "level", level,
                "severity", severity,
                "message", message,
                "httpStatus", status.toString(),
                "sourceApplication", SOURCE_APP
        );
    }

    @ExceptionHandler(CustomerNotFound.class)
    public ResponseEntity<?> handleCustomerNotFound(CustomerNotFound ex) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(
                buildError("Not Found", status,
                        ex.getMessage(),
                        "REQUEST",
                        "NONFATAL"),
                status
        );
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<?> handleAddressNotFound(AddressNotFoundException ex) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(
                buildError("Not Found", status,
                        ex.getMessage(),
                        "REQUEST",
                        "NONFATAL"),
                status
        );
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<?> handleDuplicate(DuplicateResourceException ex) {

        HttpStatus status = HttpStatus.CONFLICT;

        return new ResponseEntity<>(
                buildError("Conflict", status,
                        ex.getMessage(),
                        "REQUEST",
                        "NONFATAL"),
                status
        );
    }

    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<?> handleInvalidOperation(InvalidOperationException ex) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(
                buildError("Bad Request", status,
                        ex.getMessage(),
                        "REQUEST",
                        "NONFATAL"),
                status
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(Exception ex) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return new ResponseEntity<>(
                buildError("Internal Server Error", status,
                        "Something went wrong",
                        "SYSTEM",
                        "FATAL"),
                status
        );
    }
}
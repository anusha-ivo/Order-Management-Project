package com.ordermanagement.productservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String SOURCE_APP = "PRODUCT-SERVICE";

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

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> handleProductNotFound(ProductNotFoundException ex) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(
                buildError("Not Found",
                        status,
                        ex.getMessage(),
                        "REQUEST",
                        "NONFATAL"),
                status
        );
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<?> handleStockException(InsufficientStockException ex) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(
                buildError("Bad Request",
                        status,
                        ex.getMessage(),
                        "REQUEST",
                        "NONFATAL"),
                status
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception ex) {
        ex.printStackTrace();

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return new ResponseEntity<>(
                buildError("Internal Server Error",
                        status,
                        "Something went wrong",
                        "SYSTEM",
                        "FATAL"),
                status
        );
    }
}
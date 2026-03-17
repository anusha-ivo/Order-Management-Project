package com.ordermanagement.productservice.exceptions;

import org.springframework.http.HttpStatus;

public class InsufficientStockException extends AppException {

    public InsufficientStockException(Long productId) {
        super(
                "Insufficient stock for product: " + productId,
                HttpStatus.BAD_REQUEST,
                "Bad Request"
        );
    }
}
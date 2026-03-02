package com.ordermanagement.product_service.exceptions;

public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException(Long productId) {
        super("Insufficient stock for product id: " + productId);
    }
}
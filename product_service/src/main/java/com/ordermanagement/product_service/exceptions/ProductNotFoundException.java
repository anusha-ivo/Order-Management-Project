package com.ordermanagement.product_service.exceptions;
public class ProductNotFoundException extends RuntimeException {

        public ProductNotFoundException(Long productId) {
            super("Product not found with id: " + productId);
        }
}

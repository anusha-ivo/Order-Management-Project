package com.ordermanagement.productservice.exceptions;

import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends AppException {

        public ProductNotFoundException(Long productId) {

            super("Product not found with id: " + productId, HttpStatus.NOT_FOUND,
                    "Not Found"
            );

        }
}

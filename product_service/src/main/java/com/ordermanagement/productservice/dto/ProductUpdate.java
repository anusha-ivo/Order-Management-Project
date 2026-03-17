package com.ordermanagement.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdate {
    private String message;
    private Long productId;
    private String status;
    private Product product;
}

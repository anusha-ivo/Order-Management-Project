package com.ordermanagement.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventorydeductResponse {
    private String message;
    private Long productId;
    private Integer availableQty;
}

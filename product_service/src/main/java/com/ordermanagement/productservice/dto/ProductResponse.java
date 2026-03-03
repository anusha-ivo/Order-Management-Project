package com.ordermanagement.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private UUID productId;
    private String stock_keeping_unit;
    private String name;
    private String description;
    private BigDecimal price;
    private String currency;
    private String status;
    private Integer stock;

}

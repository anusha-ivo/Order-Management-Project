package com.ordermanagement.productservice.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private Long productId;   // assuming you switched to Long

    @NotBlank
    private String stockKeepingUnit;

    @NotBlank
    private String name;

    private String description;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;

    @NotBlank
    private String currency;

    @NotBlank
    private String status;
    private Integer availableQty;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
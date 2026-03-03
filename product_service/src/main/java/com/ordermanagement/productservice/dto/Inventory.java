package com.ordermanagement.productservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {

    @NotNull
    private Long productId;

    @NotNull
    @Min(0)
    private Integer availableQty;

    private LocalDateTime updatedAt;
}
package com.example.product_service.modules;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    private UUID productId;
    private Integer availableQty;
    private LocalDateTime updatedAt;
}

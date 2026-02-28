package com.example.product_service.modules;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

        private UUID productId;
        private String stock_keeping_unit;
        private String name;
        private String description;
        private BigDecimal price;
        private String currency;
        private String status;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;


}


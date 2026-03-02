package com.ordermanagement.product_service.controller;

import com.ordermanagement.product_service.services.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InventoryController {
    private final InventoryService inventoryService;
    InventoryController(InventoryService inventoryService){
        this.inventoryService=inventoryService;
    }
    @PostMapping("/deduct")
    public ResponseEntity<?> deductStock(@RequestParam Long productId,
                                         @RequestParam Integer quantity) {

        inventoryService.deductStock(productId, quantity);

        return ResponseEntity.ok("Stock deducted successfully");
    }
    @PostMapping("/restore")
    public ResponseEntity<?> restoreStock(@RequestParam Long productId,
                                          @RequestParam Integer quantity) {

        inventoryService.restoreStock(productId, quantity);

        return ResponseEntity.ok("Stock restored successfully");
    }
}



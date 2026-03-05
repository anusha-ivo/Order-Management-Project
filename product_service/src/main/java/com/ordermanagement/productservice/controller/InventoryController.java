package com.ordermanagement.productservice.controller;

import com.ordermanagement.productservice.dto.Inventory;
import com.ordermanagement.productservice.services.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    private final InventoryService inventoryService;
    InventoryController(InventoryService inventoryService){
        this.inventoryService=inventoryService;
    }
    @PostMapping("/deduct")
    public ResponseEntity<?> deductStock(@RequestParam Long productId,
                                         @RequestParam Integer quantity) {

        Inventory updatedInventory= inventoryService.deductStock(productId, quantity);

        return ResponseEntity.ok(updatedInventory);
    }
    @PostMapping("/restore")
    public ResponseEntity<?> restoreStock(@RequestParam Long productId,
                                          @RequestParam Integer quantity) {

        Inventory updatedInventory= inventoryService.restoreStock(productId, quantity);

        return ResponseEntity.ok(updatedInventory);
    }
}



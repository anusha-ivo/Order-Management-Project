package com.ordermanagement.productservice.controller;

import com.ordermanagement.productservice.dto.Inventory;
import com.ordermanagement.productservice.dto.InventorydeductResponse;
import com.ordermanagement.productservice.services.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Inventory API", description = "Operations for managing stock")
@RequestMapping("/inventory")
public class InventoryController {
    private final InventoryService inventoryService;
    InventoryController(InventoryService inventoryService){
        this.inventoryService=inventoryService;
    }
    @Operation(summary = "Deduct Stock", description = "Reduces product stock if available")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Stock deducted"),
            @ApiResponse(responseCode = "400", description = "Insufficient stock"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PostMapping("/deduct")
    public ResponseEntity<?> deductStock(@RequestParam Long productId,
                                         @RequestParam Integer quantity,@RequestHeader(value = "x-conversation-id", required = false)
                    String conversationId) {

        Inventory updatedInventory= inventoryService.deductStock(productId, quantity);
        InventorydeductResponse response = new   InventorydeductResponse(
                "Stock deducted successfully",
                productId,
                updatedInventory.getAvailableQty()
        );

        return ResponseEntity.ok(response);


    }
    @Operation(summary = "Restore Stock", description = "Adds stock back to product")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Stock restored"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PostMapping("/restore")
    public ResponseEntity<?> restoreStock(@RequestParam Long productId,
                                          @RequestParam Integer quantity,@RequestHeader(value = "x-conversation-id", required = false)
                                              String conversationId) {

        Inventory updatedInventory= inventoryService.restoreStock(productId, quantity);
        InventorydeductResponse response = new InventorydeductResponse(
                "Stock restored successfully",
                productId,
                updatedInventory.getAvailableQty()
        );

        return ResponseEntity.ok(response);


    }
}



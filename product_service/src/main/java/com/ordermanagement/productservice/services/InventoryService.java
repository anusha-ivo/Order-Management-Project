package com.ordermanagement.productservice.services;

import com.ordermanagement.productservice.dto.Inventory;
import com.ordermanagement.productservice.exceptions.InsufficientStockException;
import com.ordermanagement.productservice.exceptions.ProductNotFoundException;
import com.ordermanagement.productservice.repository.InventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }


    @Transactional
    public Inventory deductStock(Long productId, Integer qty) {

        int rows = inventoryRepository.deductStock(productId, qty);

        if (rows == 0) {
            throw new InsufficientStockException(productId);
        }
        return inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }


    @Transactional
    public Inventory restoreStock(Long productId, Integer qty) {

        int rows = inventoryRepository.restoreStock(productId, qty);

        if (rows == 0) {
            throw new InsufficientStockException(productId);
        }

        return inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }
}

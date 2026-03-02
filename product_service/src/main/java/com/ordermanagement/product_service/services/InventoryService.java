package com.ordermanagement.product_service.services;

import com.ordermanagement.product_service.exceptions.InsufficientStockException;
import com.ordermanagement.product_service.repository.InventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }


    @Transactional
    public void deductStock(Long productId, Integer qty) {

        int rows = inventoryRepository.deductStock(productId, qty);

        if (rows == 0) {
            throw new InsufficientStockException(productId);
        }
    }


    @Transactional
    public void restoreStock(Long productId, Integer qty) {

        int rows = inventoryRepository.restoreStock(productId, qty);

        if (rows == 0) {
            throw new InsufficientStockException(productId);
        }
    }
}

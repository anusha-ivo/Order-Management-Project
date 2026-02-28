package com.example.product_service.services;

import com.example.product_service.modules.Product;
import com.example.product_service.repository.InventoryRepository;
import com.example.product_service.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    public ProductService(ProductRepository productRepository, InventoryRepository inventoryRepository) {
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
    }

    public void createProduct(Product product,int quantity){
        UUID productId=UUID.randomUUID();
        product.setProductId(productId);
        product.setStatus("ACTIVE");
        productRepository.save(product);
        inventoryRepository.save(productId,quantity);
    }
}



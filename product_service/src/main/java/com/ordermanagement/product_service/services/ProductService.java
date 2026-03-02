package com.ordermanagement.product_service.services;

import com.ordermanagement.product_service.exceptions.ProductNotFoundException;
import com.ordermanagement.product_service.modules.Inventory;
import com.ordermanagement.product_service.modules.Product;
import com.ordermanagement.product_service.modules.ProductResponse;
import com.ordermanagement.product_service.repository.InventoryRepository;
import com.ordermanagement.product_service.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    public ProductService(ProductRepository productRepository, InventoryRepository inventoryRepository) {
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
    }
@Transactional

public Long createProduct(Product product, Integer initialStock) {
    Long productId = productRepository.create(product);
    inventoryRepository.createInitialStock(productId, initialStock);

    return productId;
}
    public Product getProduct(Long productId) {

        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException(productId);
        }

        Product product = optionalProduct.get();

        Optional<Inventory> optionalInventory =
                inventoryRepository.findByProductId(productId);

        optionalInventory.ifPresent(inventory ->
                product.setAvailableQty(inventory.getAvailableQty())
        );

        return product;
    }
    public void updateProduct(Product product) {

        int rows = productRepository.update(product);

        if (rows == 0) {
            throw new ProductNotFoundException(product.getProductId());
        }
    }

    public void deactivateProduct(Long productId) {

        int rows = productRepository.deactivate(productId);

        if (rows == 0) {
            throw new ProductNotFoundException(productId);
        }
    }


}



package com.ordermanagement.productservice.services;

import com.ordermanagement.productservice.exceptions.ProductNotFoundException;
import com.ordermanagement.productservice.dto.Inventory;
import com.ordermanagement.productservice.dto.Product;
import com.ordermanagement.productservice.repository.InventoryRepository;
import com.ordermanagement.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    public ProductService(ProductRepository productRepository, InventoryRepository inventoryRepository) {
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
    }
@Transactional

public Product createProduct(Product product, Integer initialStock) {
    Long productId = productRepository.create(product);
    inventoryRepository.createInitialStock(productId, initialStock);

    return product;
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
    public Product updateProduct(Product product) {


        productRepository.update(product);

        return productRepository.findById(product.getProductId())
                .orElseThrow(() ->   new ProductNotFoundException(product.getProductId()));


    }

    public void deactivateProduct(Long productId) {

        int rows = productRepository.deactivate(productId);

        if (rows == 0) {
            throw new ProductNotFoundException(productId);
        }
    }


}



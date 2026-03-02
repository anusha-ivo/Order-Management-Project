package com.ordermanagement.product_service.controller;


import com.ordermanagement.product_service.modules.Product;
import com.ordermanagement.product_service.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody Product product,
                                           @RequestParam Integer initialStock) {

        Long productId = productService.createProduct(product, initialStock);

        return ResponseEntity.ok("Product created with ID: " + productId);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id){
      Product product = productService.getProduct(id);
      return ResponseEntity.ok(product);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id,
                                           @RequestBody Product product) {

        product.setProductId(id);

        productService.updateProduct(product);

        return ResponseEntity.ok("Product updated successfully");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deactivateProduct(@PathVariable Long id) {

        productService.deactivateProduct(id);

        return ResponseEntity.ok("Product deactivated successfully");
    }


}

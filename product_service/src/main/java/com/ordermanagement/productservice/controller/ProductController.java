package com.ordermanagement.productservice.controller;


import com.ordermanagement.productservice.dto.Product;
import com.ordermanagement.productservice.services.ProductService;
import org.springframework.http.HttpStatus;
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

        Product productId = productService.createProduct(product, initialStock);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id){
      Product product = productService.getProduct(id);
      return ResponseEntity.ok(product);
    }
    @PutMapping("/{id}")

    public ResponseEntity<Product> updateProduct(@PathVariable Long id,
                                                 @RequestBody Product product) {

        product.setProductId(id);

        Product updatedProduct = productService.updateProduct(product);

        return ResponseEntity.ok(updatedProduct);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deactivateProduct(@PathVariable Long id) {

        productService.deactivateProduct(id);

        return ResponseEntity.ok("Product deactivated successfully");
    }


}

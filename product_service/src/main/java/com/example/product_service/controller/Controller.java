package com.example.product_service.controller;

import com.example.product_service.modules.Product;
import com.example.product_service.repository.ProductRepository;
import com.example.product_service.services.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class Controller {
    private final ProductService productService;

    public Controller(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping
    public String createProduct(@RequestBody Product product,@RequestParam int quantity){
        productService.createProduct(product,quantity);
        return "product created Successfully";
    }
}

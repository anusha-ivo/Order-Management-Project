package com.ordermanagement.productservice.controller;


import com.ordermanagement.productservice.dto.DeleteResponse;
import com.ordermanagement.productservice.dto.Product;
import com.ordermanagement.productservice.dto.ProductUpdate;
import com.ordermanagement.productservice.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Product API", description = "Operations related to product management")
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @Operation(summary = "Create Product", description = "Creates a new product with initial stock")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody Product product,
                                           @RequestParam Integer initialStock) {

        Product productId = productService.createProduct(product, initialStock);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
    @Operation(summary = "Get Product", description = "Fetch product details including available quantity")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id,@RequestHeader(value = "x-conversation-id", required = false)
    String conversationId){
      Product product = productService.getProduct(id);
      return ResponseEntity.ok(product);
    }
    @Operation(summary = "Update Product", description = "Updates product details")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product updated"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PutMapping("/{id}")

    public ResponseEntity<ProductUpdate> updateProduct(@PathVariable Long id,
                                                 @RequestBody Product product,@RequestHeader(value = "x-conversation-id", required = false)
                                                     String conversationId) {

        product.setProductId(id);

        Product updatedProduct = productService.updateProduct(product);

        ProductUpdate response = new   ProductUpdate(
                "Product updated successfully",
                id,
                updatedProduct.getStatus(),
                updatedProduct
        );

        return ResponseEntity.ok(response);
    }
    @Operation(summary = "Deactivate Product", description = "Marks product as INACTIVE")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product deactivated"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deactivateProduct(@PathVariable Long id,@RequestHeader(value = "x-conversation-id", required = false)
    String conversationId) {

        productService.deactivateProduct(id);
       DeleteResponse response = new DeleteResponse(
                "Product deactivated successfully",
                id,
                "INACTIVE"
        );

        return ResponseEntity.ok(response);


    }


}

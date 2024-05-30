package com.crm.rest.controller;

import com.crm.rest.exception.BadRequestException;
import com.crm.rest.model.Product;
import com.crm.rest.model.ProductImage;
import com.crm.rest.payload.request.ProductRequest;
import com.crm.rest.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @Operation(summary = "Create a new product", tags = "Products")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        try {
            UUID userId = UUID.fromString(productRequest.getUserId());
            UUID categoryId = UUID.fromString(productRequest.getCategory());
            Product product = new Product();
            product.setTitle(productRequest.getTitle());
            product.setDescription(productRequest.getDescription());
            product.setPrice(productRequest.getPrice());
            product = productService.createProduct(userId, categoryId, product);

            return ResponseEntity.ok(product);
        } catch (Exception e) {
            throw new BadRequestException("Error creating product: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a product by ID", tags = "Products")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    public ResponseEntity<Product> getProductById(@PathVariable UUID id) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            throw new BadRequestException("Product not found: " + e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get products by user ID", tags = "Products")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    public ResponseEntity<List<Product>> getProductsByUser(@PathVariable UUID userId) {
        try {
            List<Product> products = productService.getProductsByUser(userId);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            throw new BadRequestException("User not found: " + e.getMessage());
        }
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Get products by category ID", tags = "Products")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable UUID categoryId) {
        try {
            List<Product> products = productService.getProductsByCategory(categoryId);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            throw new BadRequestException("Category not found: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a product", tags = "Products")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    public ResponseEntity<Product> updateProduct(
            @PathVariable UUID id,
            @RequestBody @Valid ProductRequest productRequest) {
        try {
            Product product = productService.getProductById(id);
            product.setTitle(productRequest.getTitle());
            product.setDescription(productRequest.getDescription());
            product.setPrice(productRequest.getPrice());

            // Update other necessary fields and handle the images if needed
            Product updatedProduct = productService.updateProduct(product);

            return ResponseEntity.ok(updatedProduct);
        } catch (Exception e) {
            throw new BadRequestException("Product not found: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product", tags = "Products")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new BadRequestException("Product not found: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/images")
    @Operation(summary = "Add images to a product", tags = "Products")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Images added successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    public ResponseEntity<Void> addImagesToProduct(
            @PathVariable UUID id,
            @RequestParam("images") Set<MultipartFile> images) {
        try {
            // Convert MultipartFile to ProductImage and call the service method
            Set<ProductImage> productImages = images.stream().map(image -> {
                ProductImage productImage = new ProductImage();
                productImage.setProduct(productService.getProductById(id));
                try {
                    productImage.setData(image.getBytes()); // assuming you have a method to convert MultipartFile to bytes
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return productImage;
            }).collect(Collectors.toSet());

            productService.addImageToProduct(id, productImages);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new BadRequestException("Error adding images: " + e.getMessage());
        }
    }
}

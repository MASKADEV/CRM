package com.crm.rest.service;

import com.crm.rest.model.*;
import com.crm.rest.repository.CategoryRepository;
import com.crm.rest.repository.ImageRepository;
import com.crm.rest.repository.ProductRepository;
import com.crm.rest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ImageRepository imageRepository;

    // Create a new user
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Create a new category
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    // Create a new product and associate it with a user and a category
    @Transactional
    public Product createProduct(UUID userId, UUID categoryId, Product product) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));

        product.setUser(user);
        product.setCategory(category);

        return productRepository.save(product);
    }

    @Transactional
    public void addImageToProduct(UUID productId, Set<ProductImage> images) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setImages(images);
        images.stream().forEach(img -> imageRepository.save(img));
    }

    // Get all products associated with a user
    public List<Product> getProductsByUser(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return List.copyOf(user.getProducts());
    }

    // Get all products associated with a user
    public Product getProductById(UUID productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("User not found"));
        return product;
    }

    // Get all products associated with a category
    public List<Product> getProductsByCategory(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
        return List.copyOf(category.getProducts());
    }

    //update product
    public Product updateProduct(Product product) {
        Product product1 = productRepository.save(product);
        return product1;
    }

    //update product
    public void deleteProduct(UUID productId) {
        Product product1 = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(product1);
    }
}

package com.crm.rest.service;

import com.crm.rest.model.Category;
import com.crm.rest.payload.request.CategoryRequest;
import com.crm.rest.repository.CategoryRepository;
import com.crm.rest.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    private static final Logger log = LoggerFactory.getLogger(CategoryService.class);
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }

    public Category getCategoryById(String id) {
        Category category = categoryRepository.findById(UUID.fromString(id)).orElseThrow(() -> new RuntimeException("Category not found"));
        return category;
    }

    @Transactional
    public CategoryRequest saveCategory(CategoryRequest categoryRequest) {
        Category category = Category.builder()
                .name(categoryRequest.getTitle())
                .description(categoryRequest.getDescription())
                .build();

        category = categoryRepository.save(category);

        if (category != null && !category.getName().isEmpty()) {
            log.info("Category saved successfully");
            return CategoryRequest.builder()
                    .id(category.getId().toString())
                    .title(category.getName())
                    .description(category.getDescription())
                    .build();
        } else {
            return null;
        }
    }

    @Transactional
    public CategoryRequest updateCategory(CategoryRequest categoryReq) {
        Category category = getCategoryById(UUID.fromString(categoryReq.getId()));
        if (category != null) {
            category.setName(categoryReq.getTitle());
            category.setDescription(categoryReq.getDescription());

            category = categoryRepository.save(category);
            if (!category.getName().isEmpty()) {
                return CategoryRequest.builder()
                        .id(category.getId().toString())
                        .title(category.getName())
                        .description(category.getDescription())
                        .build();
            }
        }
        return null;
    }

    private Category getCategoryById(UUID id) {
        return categoryRepository.findById(id).orElse(null);
    }
}

package com.crm.rest.service;

import com.crm.rest.model.Category;
import com.crm.rest.payload.request.CategoryRequest;
import com.crm.rest.repository.CategoryRepository;
import com.crm.rest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

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

    public CategoryRequest saveCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.builder().name(categoryRequest.getTitle()).description(categoryRequest.getDescription()).build();
        if(!categoryRepository.save(category).getName().isEmpty()){
            CategoryRequest categoryReq1 = new CategoryRequest();
            categoryReq1.builder().title(category.getName()).description(category.getDescription()).id(category.getId().toString()).build();
            return categoryReq1;
        }else {
            return null;
        }
    }

    public CategoryRequest updateCategory(CategoryRequest categoryReq) {
        Category category = getCategoryById(categoryReq.getId());
        if(!categoryRepository.save(category).getName().isEmpty()){
            CategoryRequest categoryReq1 = new CategoryRequest();
            categoryReq1.builder().title(category.getName()).description(category.getDescription()).id(category.getId().toString()).build();
            return categoryReq1;
        }else {
            return null;
        }
    }
}

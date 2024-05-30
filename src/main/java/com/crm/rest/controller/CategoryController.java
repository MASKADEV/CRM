package com.crm.rest.controller;


import com.crm.rest.payload.request.CategoryRequest;
import com.crm.rest.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    @Operation(summary = "Create a new category", tags = "category")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "category has been added"),
            @ApiResponse(responseCode = "400", description = "invalid input"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    public ResponseEntity<CategoryRequest> createCategory(@RequestBody CategoryRequest categoryReq) {
        return ResponseEntity.ok(categoryService.saveCategory(categoryReq));
    }

    @PutMapping
    @Operation(summary = "update category", tags = "category")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "category has been updated"),
            @ApiResponse(responseCode = "400", description = "invalid input"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    public ResponseEntity<CategoryRequest> updateCategory(@RequestBody CategoryRequest categoryReq) {
        return ResponseEntity.ok(categoryService.updateCategory(categoryReq));
    }

}

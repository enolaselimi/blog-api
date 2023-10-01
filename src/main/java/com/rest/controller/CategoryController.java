package com.rest.controller;

import com.rest.domain.dto.CategoryRequest;
import com.rest.domain.dto.CategoryResponse;
import com.rest.domain.dto.UserRequest;
import com.rest.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/api/blog/categories")
    public ResponseEntity<Void> createCategory(@RequestBody CategoryRequest categoryRequest, UriComponentsBuilder ucb){
        var createdCategory = categoryService.createCategory(categoryRequest);
        URI locationOfCreatedCategory = ucb
                .path("/api/blog/category")
                .buildAndExpand(createdCategory.getId())
                .toUri();
        return ResponseEntity.created(locationOfCreatedCategory).build();
    }

    @DeleteMapping("/api/categories/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer categoryId){
        this.categoryService.deleteCategory(categoryId);
        return  ResponseEntity.noContent().build();
    }

    @GetMapping("/api/categories")
    public ResponseEntity<List<CategoryResponse>> getPostCategories(){
        return ResponseEntity.ok(this.categoryService.listPostCategories());
    }
}

package com.rest.service;

import com.rest.domain.dto.CategoryRequest;
import com.rest.domain.dto.CategoryResponse;
import com.rest.domain.model.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryRequest categoryRequest);
    boolean deleteCategory(Integer categoryId);
    List<CategoryResponse> listPostCategories();
}

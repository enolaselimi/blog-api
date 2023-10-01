package com.rest.service.impl;

import com.rest.BlogDataSource;
import com.rest.domain.dto.CategoryRequest;
import com.rest.domain.dto.CategoryResponse;
import com.rest.domain.model.Category;
import com.rest.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private BlogDataSource dataSource;

    @Override
    public Category createCategory(CategoryRequest categoryRequest) {
        var index = dataSource.getCategories().size()+1;
        var category = new Category(index, categoryRequest.getName());
        return dataSource.getCategories().add(category) ? category : null;
    }

    @Override
    public boolean deleteCategory(Integer categoryId) {
        //step 1 delete all registration for deleted player
        var categoryToDelete = this.dataSource.getCategories().stream()
                .filter(c -> c.getId() == categoryId)
                .findFirst().orElse(null);
        return this.dataSource.getCategories().remove(categoryToDelete);
    }

    @Override
    public List<CategoryResponse> listPostCategories() {
        List<CategoryResponse> categories = dataSource.getCategories().stream()
                .map(c -> new CategoryResponse(c.getId(), c.getName()))
                .collect(Collectors.toList());
        return categories;
    }
}

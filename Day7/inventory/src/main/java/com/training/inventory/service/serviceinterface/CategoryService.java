package com.training.inventory.service.serviceinterface;


import com.training.inventory.dto.CategoryDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {

    ResponseEntity<Void> addCategory(CategoryDTO categoryDTO);

    ResponseEntity<List<CategoryDTO>> getAllCategories();

    ResponseEntity<CategoryDTO> getCategoryById(Long id);

    void evictAllCategoriesCache();

    ResponseEntity<Void> updateCategory(Long id, CategoryDTO categoryDTO);

    ResponseEntity<Void> deleteCategory(Long id);
}
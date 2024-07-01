package com.training.inventory.service.impl;

import com.training.inventory.dto.CategoryDTO;
import com.training.inventory.entity.Category;
import com.training.inventory.entity.Product;
import com.training.inventory.repository.CategoryRepository;
import com.training.inventory.service.serviceinterface.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<Void> addCategory(CategoryDTO categoryDTO) {
        try {
            Category category = new Category();
            category.setName(categoryDTO.getName());
            categoryRepository.save(category);
            logger.info("Category added successfully: {}", category);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error adding category: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        try {
            List<Category> categoryList = categoryRepository.findAll();
            List<CategoryDTO> categoryDTOList = categoryList.stream().map(this::convertToDTO).collect(Collectors.toList());
            logger.info("Fetched all categories successfully");
            return new ResponseEntity<>(categoryDTOList, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching all categories: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<CategoryDTO> getCategoryById(Long id) {
        try {
            Optional<Category> categoryOptional = categoryRepository.findById(id);
            if (categoryOptional.isPresent()) {
                logger.info("Category fetched by ID successfully: {}", id);
                return new ResponseEntity<>(convertToDTO(categoryOptional.get()), HttpStatus.OK);
            } else {
                logger.warn("Category not found by ID: {}", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error fetching category by ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @CacheEvict(value = "categories", allEntries = true)
    public void evictAllCategoriesCache() {
        logger.info("Evicted all categories cache");
    }

    @Override
    public ResponseEntity<Void> updateCategory(Long id, CategoryDTO categoryDTO) {
        try {
            Optional<Category> categoryOptional = categoryRepository.findById(id);
            if (categoryOptional.isPresent()) {
                Category existingCategory = categoryOptional.get();
                existingCategory.setName(categoryDTO.getName());
                categoryRepository.save(existingCategory);
                logger.info("Category updated successfully: {}", id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                logger.warn("Category not found for update: {}", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error updating category: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Void> deleteCategory(Long id) {
        try {
            categoryRepository.deleteById(id);
            logger.info("Category deleted successfully: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error deleting category: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private CategoryDTO convertToDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setProductIds(category.getProducts().stream().map(Product::getId).collect(Collectors.toList()));
        return categoryDTO;
    }
}
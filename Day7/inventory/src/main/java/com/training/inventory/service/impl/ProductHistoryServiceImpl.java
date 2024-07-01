package com.training.inventory.service.impl;

import com.training.inventory.dto.ProductHistoryDTO;
import com.training.inventory.entity.ProductHistory;
import com.training.inventory.repository.ProductHistoryRepository;
import com.training.inventory.service.serviceinterface.ProductHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductHistoryServiceImpl implements ProductHistoryService {

    @Autowired
    private ProductHistoryRepository productHistoryRepository;

    @Override
    public void addProductHistory(ProductHistory productHistory) {
        productHistoryRepository.save(productHistory);
    }

    @Override
    public List<ProductHistoryDTO> getAllProductHistory() {
        return productHistoryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ProductHistoryDTO convertToDTO(ProductHistory productHistory) {
        ProductHistoryDTO dto = new ProductHistoryDTO();
        dto.setId(productHistory.getId());
        dto.setOldValue(productHistory.getOldValue());
        dto.setNewValue(productHistory.getNewValue());
        dto.setColumnName(productHistory.getColumnName());
        dto.setProductId(productHistory.getProduct().getId());
        dto.setModifiedBy(productHistory.getModifiedBy());
        dto.setCreatedAt(productHistory.getCreatedAt());
        return dto;
    }
}
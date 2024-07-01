package com.training.inventory.service.serviceinterface;

import com.training.inventory.dto.ProductHistoryDTO;
import com.training.inventory.entity.ProductHistory;

import java.util.List;

public interface ProductHistoryService {
    void addProductHistory(ProductHistory productHistory);
    List<ProductHistoryDTO> getAllProductHistory();
}
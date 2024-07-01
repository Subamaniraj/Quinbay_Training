package com.training.inventory.controller;

import com.training.inventory.dto.ProductHistoryDTO;
import com.training.inventory.service.impl.ProductHistoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory/productHistory")
public class ProductHistoryController {

    @Autowired
    private ProductHistoryServiceImpl productHistoryService;

    @GetMapping("/getAllProductHistory")
    public ResponseEntity<List<ProductHistoryDTO>> getAllProductHistory() {
        return ResponseEntity.ok(productHistoryService.getAllProductHistory());
    }
}
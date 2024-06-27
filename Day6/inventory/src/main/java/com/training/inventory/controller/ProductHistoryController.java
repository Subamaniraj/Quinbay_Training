package com.training.inventory.controller;

import com.training.inventory.dto.ProductHistoryDTO;
import com.training.inventory.service.ProductHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productHistory")
public class ProductHistoryController {

    @Autowired
    private ProductHistoryService productHistoryService;

    @GetMapping("/getAllProductHistory")
    public ResponseEntity<List<ProductHistoryDTO>> getAllProductHistory() {
        return ResponseEntity.ok(productHistoryService.getAllProductHistory());
    }
}
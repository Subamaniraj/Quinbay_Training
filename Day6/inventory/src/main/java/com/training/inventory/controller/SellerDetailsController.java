package com.training.inventory.controller;

import com.training.inventory.dto.SellerDetailsDTO;
import com.training.inventory.service.SellerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/sellerDetails")
public class SellerDetailsController {

    @Autowired
    private SellerDetailsService sellerDetailsService;

    @PostMapping("/add")
    public ResponseEntity<Void> addSellerDetails(@RequestBody SellerDetailsDTO sellerDetailsDTO) {
        return sellerDetailsService.addSellerDetails(sellerDetailsDTO);
    }

    @GetMapping("/getAllSellerDetails")
    public ResponseEntity<List<SellerDetailsDTO>> getAllSellerDetails() {
        return sellerDetailsService.getAllSellerDetails();
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<SellerDetailsDTO> getSellerDetailsById(@PathVariable Long id) {
        return sellerDetailsService.getSellerDetailsById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateSellerDetails(@PathVariable Long id, @RequestBody SellerDetailsDTO sellerDetailsDTO) {
        return sellerDetailsService.updateSellerDetails(id, sellerDetailsDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSellerDetails(@PathVariable Long id) {
        return sellerDetailsService.deleteSellerDetails(id);
    }
}
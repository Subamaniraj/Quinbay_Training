package com.training.inventory.service.serviceinterface;

import com.training.inventory.dto.SellerDetailsDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SellerDetailsService {
    ResponseEntity<Void> addSellerDetails(SellerDetailsDTO sellerDetailsDTO);
    ResponseEntity<List<SellerDetailsDTO>> getAllSellerDetails();
    ResponseEntity<SellerDetailsDTO> getSellerDetailsById(Long id);
    ResponseEntity<Void> updateSellerDetails(Long id, SellerDetailsDTO sellerDetailsDTO);
    ResponseEntity<Void> deleteSellerDetails(Long id);
}
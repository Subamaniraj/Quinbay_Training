package com.training.inventory.service.serviceinterface;;

import com.training.inventory.dto.ProductDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    ResponseEntity<Void> addProduct(ProductDTO productDTO);
    ResponseEntity<List<ProductDTO>> getAllProducts();
    ResponseEntity<ProductDTO> getProductById(Long id);
    ResponseEntity<Void> updateProduct(Long id, ProductDTO productDTO);
    ResponseEntity<Void> deleteProduct(Long id);
    ResponseEntity<Void> decrementStockAll();
}
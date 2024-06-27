package com.training.inventory.service;

import com.training.inventory.dto.ProductDTO;
import com.training.inventory.entity.Product;
import com.training.inventory.entity.ProductHistory;
import com.training.inventory.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SellerDetailsRepository sellerDetailsRepository;

    @Autowired
    private ProductHistoryRepository productHistoryRepository;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public ResponseEntity<Void> addProduct(ProductDTO productDTO) {
        try {
            Product product = new Product();
            product.setName(productDTO.getName());
            product.setStock(productDTO.getStock());
            product.setPrice(productDTO.getPrice());
            product.setCategory(categoryRepository.findById(productDTO.getCategoryId()).orElseThrow());
            product.setSellerDetails(sellerDetailsRepository.findById(productDTO.getSellerId()).orElseThrow());
            productRepository.save(product);
            logger.info("Product added successfully: {}", product);
            //kafkaTemplate.send("confirmation","Product added successfully");
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error adding product: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        try {
            List<Product> productList = productRepository.findAll();
            List<ProductDTO> productDTOList = productList.stream().map(this::convertToDTO).collect(Collectors.toList());
            logger.info("Fetched all products successfully");
            kafkaTemplate.send("confirmation","Product retrieved successfully");
            return new ResponseEntity<>(productDTOList, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching all products: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ProductDTO> getProductById(Long id) {
        try {

            Optional<Product> productOptional = productRepository.findById(id);
            if (productOptional.isPresent()) {
                logger.info("Product fetched by ID successfully: {}", id);
                return new ResponseEntity<>(convertToDTO(productOptional.get()), HttpStatus.OK);
            } else {
                logger.warn("Product not found by ID: {}", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error fetching product by ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Void> updateProduct(Long id, ProductDTO productDTO) {
        try {
            Optional<Product> productOptional = productRepository.findById(id);
            if (productOptional.isPresent()) {
                Product existingProduct = productOptional.get();

                logProductHistory(existingProduct, productDTO, "name", existingProduct.getName(), productDTO.getName());
                logProductHistory(existingProduct, productDTO, "stock", String.valueOf(existingProduct.getStock()), String.valueOf(productDTO.getStock()));
                logProductHistory(existingProduct, productDTO, "price", String.valueOf(existingProduct.getPrice()), String.valueOf(productDTO.getPrice()));
                logProductHistory(existingProduct, productDTO, "category_id", String.valueOf(existingProduct.getCategory().getId()), String.valueOf(productDTO.getCategoryId()));
                logProductHistory(existingProduct, productDTO, "seller_id", String.valueOf(existingProduct.getSellerDetails().getId()), String.valueOf(productDTO.getSellerId()));

                existingProduct.setName(productDTO.getName());
                existingProduct.setStock(productDTO.getStock());
                existingProduct.setPrice(productDTO.getPrice());
                existingProduct.setCategory(categoryRepository.findById(productDTO.getCategoryId()).orElseThrow());
                existingProduct.setSellerDetails(sellerDetailsRepository.findById(productDTO.getSellerId()).orElseThrow());
                productRepository.save(existingProduct);
                logger.info("Product updated successfully: {}", id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                logger.warn("Product not found for update: {}", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error updating product: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Void> deleteProduct(Long id) {
        try {
            productRepository.deleteById(id);
            logger.info("Product deleted successfully: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error deleting product: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Void> decrementStockAll() {
        try {
            List<Product> products = productRepository.findAll();
            for (Product product : products) {
                if (product.getStock() > 0) {
                    product.setStock(product.getStock() - 1);
                }
            }
            productRepository.saveAll(products);
            logger.info("Stock decremented for all products successfully");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error decrementing stock for all products", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void logProductHistory(Product existingProduct, ProductDTO productDTO, String columnName, String oldValue, String newValue) {
        if (!oldValue.equals(newValue)) {
            ProductHistory productHistory = new ProductHistory();
            productHistory.setOldValue(oldValue);
            productHistory.setNewValue(newValue);
            productHistory.setColumnName(columnName);
            productHistory.setProduct(existingProduct);
            productHistory.setModifiedBy(productDTO.getSellerId());
            productHistory.setCreatedAt(new Date());
            productHistoryRepository.save(productHistory);
            logger.info("Product history logged for column: {}", columnName);
        }
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setStock(product.getStock());
        productDTO.setPrice(product.getPrice());
        productDTO.setCategoryId(product.getCategory().getId());
        productDTO.setSellerId(product.getSellerDetails().getId());
        return productDTO;
    }
}
package com.training.inventory.service;

import com.training.inventory.dto.SellerDetailsDTO;
import com.training.inventory.entity.SellerDetails;
import com.training.inventory.repository.SellerDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SellerDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(SellerDetailsService.class);

    @Autowired
    private SellerDetailsRepository sellerDetailsRepository;

    public ResponseEntity<Void> addSellerDetails(SellerDetailsDTO sellerDetailsDTO) {
        try {
            SellerDetails sellerDetails = new SellerDetails();
            sellerDetails.setName(sellerDetailsDTO.getName());
            sellerDetails.setAddress(sellerDetailsDTO.getAddress());
            sellerDetailsRepository.save(sellerDetails);
            logger.info("Seller details added successfully: {}", sellerDetails);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error adding seller details: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<SellerDetailsDTO>> getAllSellerDetails() {
        try {
            List<SellerDetails> sellerDetailsList = sellerDetailsRepository.findAll();
            List<SellerDetailsDTO> sellerDetailsDTOList = sellerDetailsList.stream().map(this::convertToDTO).collect(Collectors.toList());
            logger.info("Fetched all seller details successfully");
            return new ResponseEntity<>(sellerDetailsDTOList, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching all seller details: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<SellerDetailsDTO> getSellerDetailsById(Long id) {
        try {
            Optional<SellerDetails> sellerDetailsOptional = sellerDetailsRepository.findById(id);
            if (sellerDetailsOptional.isPresent()) {
                logger.info("Seller details fetched by ID successfully: {}", id);
                return new ResponseEntity<>(convertToDTO(sellerDetailsOptional.get()), HttpStatus.OK);
            } else {
                logger.warn("Seller details not found by ID: {}", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error fetching seller details by ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Void> updateSellerDetails(Long id, SellerDetailsDTO sellerDetailsDTO) {
        try {
            Optional<SellerDetails> sellerDetailsOptional = sellerDetailsRepository.findById(id);
            if (sellerDetailsOptional.isPresent()) {
                SellerDetails existingSellerDetails = sellerDetailsOptional.get();
                existingSellerDetails.setName(sellerDetailsDTO.getName());
                existingSellerDetails.setAddress(sellerDetailsDTO.getAddress());
                sellerDetailsRepository.save(existingSellerDetails);
                logger.info("Seller details updated successfully: {}", id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                logger.warn("Seller details not found for update: {}", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error updating seller details: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Void> deleteSellerDetails(Long id) {
        try {
            sellerDetailsRepository.deleteById(id);
            logger.info("Seller details deleted successfully: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error deleting seller details: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private SellerDetailsDTO convertToDTO(SellerDetails sellerDetails) {
        SellerDetailsDTO sellerDetailsDTO = new SellerDetailsDTO();
        sellerDetailsDTO.setId(sellerDetails.getId());
        sellerDetailsDTO.setName(sellerDetails.getName());
        sellerDetailsDTO.setAddress(sellerDetails.getAddress());
        sellerDetailsDTO.setProductIds(sellerDetails.getProducts().stream().map(product -> product.getId()).collect(Collectors.toList()));
        return sellerDetailsDTO;
    }
}
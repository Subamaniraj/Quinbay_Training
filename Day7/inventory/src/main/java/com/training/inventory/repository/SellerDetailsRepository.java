package com.training.inventory.repository;

import com.training.inventory.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface SellerDetailsRepository extends JpaRepository<SellerDetails,Long> {
}
package com.training.reporting.repository;

import com.training.reporting.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
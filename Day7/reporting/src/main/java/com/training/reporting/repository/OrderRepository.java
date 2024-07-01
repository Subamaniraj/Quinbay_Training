package com.training.reporting.repository;

import com.training.reporting.entity.Order;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByOrderId(String orderId);

    List<Order> findByOrderDateBetween(Date startDate, Date endDate);

    List<Order> findByTotalCostBetween(double minCost, double maxCost);

    @Query("SELECT o FROM Order o WHERE o.orderId = :orderId OR o.orderDate = :orderDate OR o.totalCost = :totalCost")
    List<Order> findByCustomCriteria(@Param("orderId") String orderId,
                                     @Param("orderDate") Date orderDate,
                                     @Param("totalCost") Double totalCost);
}
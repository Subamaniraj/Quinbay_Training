package com.training.order.repository;

import com.training.order.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    Order findFirstByStatusOrderByOrderIdDesc(String status);
}
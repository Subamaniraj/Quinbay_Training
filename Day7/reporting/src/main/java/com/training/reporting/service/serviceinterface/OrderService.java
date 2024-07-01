package com.training.reporting.service;

import com.training.reporting.entity.Order;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.List;

public interface OrderService {

    void consume(Order order);

    List<Order> getOrdersByOrderId(String orderId);

    List<Order> getOrdersByDateRange(Date startDate, Date endDate);

    List<Order> getOrdersByCostRange(double minCost, double maxCost);

    List<Order> getOrdersByCustomCriteria(String orderId, Date orderDate, Double totalCost);

    ByteArrayInputStream exportOrdersToExcel();

    void sendEmail(String to, String subject, String text);
}
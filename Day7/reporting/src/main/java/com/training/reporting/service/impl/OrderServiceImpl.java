package com.training.reporting.service.impl;

import com.training.reporting.entity.Order;
import com.training.reporting.entity.OrderItem;
import com.training.reporting.repository.OrderItemRepository;
import com.training.reporting.repository.OrderRepository;
import com.training.reporting.service.OrderService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    @KafkaListener(topics = "order_topic", groupId = "order_group")
    public void consume(Order order) {
        try {
            order.setOrderDate(new Date());

            List<OrderItem> orderItems = order.getOrderItems();
            if (orderItems == null) {
                orderItems = new ArrayList<>();
                order.setOrderItems(orderItems);
            }

            order = orderRepository.save(order);

            for (OrderItem item : orderItems) {
                item.setOrder(item.getOrder());
                orderItemRepository.save(item);
            }
            String subject = "Thank you for your payment";
            String text = "Dear Customer,\n\nThank you for your payment.\n\nOrder Details:\nOrder ID: " + order.getOrderId() + "\nTotal Products: " + order.getTotalProducts() + "\nTotal Cost: $" + order.getTotalCost() + "\n\nBest regards,\nYour Company";
            sendEmail(order.getUserEmail(), subject, text);

        } catch (Exception e) {
            // Log any other exceptions
            System.err.println("Exception in consumer: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<Order> getOrdersByOrderId(String orderId) {
        return orderRepository.findByOrderId(orderId);
    }

    @Override
    public List<Order> getOrdersByDateRange(Date startDate, Date endDate) {
        return orderRepository.findByOrderDateBetween(startDate, endDate);
    }

    @Override
    public List<Order> getOrdersByCostRange(double minCost, double maxCost) {
        return orderRepository.findByTotalCostBetween(minCost, maxCost);
    }

    @Override
    public List<Order> getOrdersByCustomCriteria(String orderId, Date orderDate, Double totalCost) {
        return orderRepository.findByCustomCriteria(orderId, orderDate, totalCost);
    }

    @Override
    public ByteArrayInputStream exportOrdersToExcel() {
        List<Order> orders = orderRepository.findAll();

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Orders");

            // Header
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Order ID", "User Email", "Total Products", "Total Cost", "Order Date"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Data
            int rowIdx = 1;
            for (Order order : orders) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(order.getOrderId());
                row.createCell(1).setCellValue(order.getUserEmail());
                row.createCell(2).setCellValue(order.getTotalProducts());
                row.createCell(3).setCellValue(order.getTotalCost());
                row.createCell(4).setCellValue(order.getOrderDate().toString());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
package com.training.reporting.controller;

import com.training.reporting.entity.*;
import com.training.reporting.service.*;
 import com.training.reporting.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.*;

@RestController
@RequestMapping("/reports")
public class ReportingController {

    @Autowired
    private OrderServiceImpl orderService;

    @GetMapping("/download/excel")
    public ResponseEntity<InputStreamResource> downloadOrdersExcel() {
        ByteArrayInputStream in = orderService.exportOrdersToExcel();
        if (in == null) {
            return ResponseEntity.internalServerError().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=orders.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(in));
    }
    @GetMapping("/filter")
    public List<Order> filterOrders(
            @RequestParam(required = false) String orderId,
            @RequestParam(required = false) Date orderDate,
            @RequestParam(required = false) Double totalCost) {
        return orderService.getOrdersByCustomCriteria(orderId, orderDate, totalCost);
    }
}
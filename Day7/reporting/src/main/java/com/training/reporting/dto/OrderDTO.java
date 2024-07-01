package com.training.reporting.dto;

import java.util.*;

public class OrderDTO {
    private String orderId;
    private int userId;
    private String userEmail;
    private List<ProductDTO> productList;

    public OrderDTO(){

    }

    public OrderDTO(String orderId, int userId, String userEmail, List<ProductDTO> productList, int totalProducts, double totalCost, String status) {
        this.orderId = orderId;
        this.userId = userId;
        this.userEmail = userEmail;
        this.productList = productList;
        this.totalProducts = totalProducts;
        this.totalCost = totalCost;
        this.status = status;
    }

    private int totalProducts;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public List<ProductDTO> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductDTO> productList) {
        this.productList = productList;
    }

    public int getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(int totalProducts) {
        this.totalProducts = totalProducts;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private double totalCost;
    private String status;
    public static class ProductDTO {
        private long id;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        private String name;
        private int stock;

        public ProductDTO(){

        }

        public ProductDTO(long id, String name, int stock, double price) {
            this.id = id;
            this.name = name;
            this.stock = stock;
            this.price = price;
        }

        private double price;
    }
}
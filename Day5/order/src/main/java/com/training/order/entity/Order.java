package com.training.order.entity;

import java.util.List;

public class Order {
    private String orderId;
    private int userId;
    private List<Product> productList;
    private int totalProducts;
    private double totalCost;
    private String status;

    public Order() {
    }

    public Order(String orderId, int userId, List<Product> productList, int totalProducts, double totalCost, String status) {
        this.orderId = orderId;
        this.userId = userId;
        this.productList = productList;
        this.totalProducts = totalProducts;
        this.totalCost = totalCost;
        this.status = status;
    }

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

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
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

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", userId=" + userId +
                ", productList=" + productList +
                ", totalProducts=" + totalProducts +
                ", totalCost=" + totalCost +
                ", status='" + status + '\'' +
                '}';
    }
}
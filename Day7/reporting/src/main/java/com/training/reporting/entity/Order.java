package com.training.reporting.entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Id
    @Column(name = "order_id")
    private String orderId;

    public Order(){

    }
    public Order(String orderId,int userId, String userEmail, int totalProducts, double totalCost, Date orderDate, List<OrderItem> orderItems) {
        this.orderId = orderId;
        this.userId=userId;
        this.userEmail = userEmail;
        this.totalProducts = totalProducts;
        this.totalCost = totalCost;
        this.orderDate = orderDate;
        this.orderItems = orderItems;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Column(name="user_id")
    private int userId;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "total_products")
    private int totalProducts;

    @Column(name = "total_cost")
    private double totalCost;

    @Column(name = "order_date")
    private Date orderDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

}
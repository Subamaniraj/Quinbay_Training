package com.training.order.entity;

public class Product {

    private Long id;
    private String name;
    private int stock;
    private double price;

    public Product() {
    }

    public Product(Long id, String name, int stock,double price) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", stock= 1, price=" + price +
                '}';
    }
}
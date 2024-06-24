package org.training.day4;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private final Connection connection;

    public OrderDAO() throws SQLException {
        this.connection = DatabaseConnection.getPostgresConnection();
    }

    // Method to add a new order
    public void addOrder(Order order) throws SQLException {
        String sql = "INSERT INTO orders (orderid, total_products, total_cost) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, order.getOrderId());
            statement.setInt(2, order.getTotalProducts());
            statement.setDouble(3, order.getTotalCost());
            statement.executeUpdate();
        }
    }

    // Method to add a new order item
    public void addOrderItem(OrderItem orderItem) throws SQLException {
        String sql = "INSERT INTO order_items (orderitemid, orderid, productid, cost_per_product) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderItem.getOrderItemId());
            statement.setInt(2, orderItem.getOrderId());
            statement.setInt(3, orderItem.getProductId());
            statement.setDouble(4, orderItem.getCostPerProduct());
            statement.executeUpdate();
        }
    }

    // Method to retrieve all orders
    public List<Order> getAllOrders() throws SQLException {
        String sql = "SELECT * FROM orders";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                Order order = new Order(
                        resultSet.getInt("orderid"),
                        resultSet.getInt("total_products"),
                        resultSet.getDouble("total_cost")
                );
                orders.add(order);
            }
            return orders;
        }
    }

    // Method to retrieve all order items for a given order ID
    public List<OrderItem> getOrderItems(int orderId) throws SQLException {
        String sql = "SELECT * FROM order_items WHERE orderid = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderId);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<OrderItem> orderItems = new ArrayList<>();
                while (resultSet.next()) {
                    OrderItem orderItem = new OrderItem(
                            resultSet.getInt("orderitemid"),
                            resultSet.getInt("orderid"),
                            resultSet.getInt("productid"),
                            resultSet.getDouble("cost_per_product")
                    );
                    orderItems.add(orderItem);
                }
                return orderItems;
            }
        }
    }
    public List<OrderItem> getOrderItemsByProductId(int productId) throws SQLException {
        String sql = "SELECT * FROM order_items WHERE productid = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<OrderItem> orderItems = new ArrayList<>();
                while (resultSet.next()) {
                    OrderItem orderItem = new OrderItem(
                            resultSet.getInt("orderitemid"),
                            resultSet.getInt("orderid"),
                            resultSet.getInt("productid"),
                            resultSet.getDouble("cost_per_product")
                    );
                    orderItems.add(orderItem);
                }
                return orderItems;
            }
        }
    }

    // Method to delete an order
    public void deleteOrder(int orderId) throws SQLException {
        String sql = "DELETE FROM orders WHERE orderid = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderId);
            statement.executeUpdate();
        }
    }

    // Method to delete order items for a given order ID
    public void deleteOrderItems(int orderId) throws SQLException {
        String sql = "DELETE FROM order_items WHERE orderid = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderId);
            statement.executeUpdate();
        }
    }
}

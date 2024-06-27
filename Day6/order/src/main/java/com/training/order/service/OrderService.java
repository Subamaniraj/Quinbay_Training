package com.training.order.service;

import com.training.order.entity.Order;
import com.training.order.entity.Product;
import com.training.order.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrderRepository orderRepository;

    public List<Product> getAllProducts() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<>(headers);
            Product[] products = restTemplate.exchange("http://localhost:8080/product/getAllProducts", HttpMethod.GET, entity, Product[].class).getBody();
            return Arrays.asList(products);
        } catch (Exception e) {
            logger.error("Error occurred while fetching products: {}", e.getMessage());
            throw e;
        }
    }

    public void addToCart(int userId) {
        try {
            List<Product> products = getAllProducts();
            List<Product> availableProducts = new ArrayList<>();

            for (Product product : products) {
                if (product.getStock() > 0) {
                    availableProducts.add(product);
                }
            }

            if (availableProducts.isEmpty()) {
                return;
            }

            int totalProducts = availableProducts.size();
            double totalCost = availableProducts.stream().mapToDouble(Product::getPrice).sum();
            Order cart = new Order(null, userId, availableProducts, totalProducts, totalCost, "CART");

            orderRepository.save(cart);
        } catch (Exception e) {
            logger.error("Error occurred while adding items to cart for user {}: {}", userId, e.getMessage());
            throw e;
        }
    }

    public Order placeOrder() {
        try {
            Order cart = orderRepository.findFirstByStatusOrderByOrderIdDesc("CART");
            if (cart != null) {
                cart.setStatus("PLACED");
                orderRepository.save(cart);
                decrementAllProductStock();
                return cart;
            }
            return null;
        } catch (Exception e) {
            logger.error("Error occurred while placing order: {}", e.getMessage());
            throw e;
        }
    }

    public boolean deleteCart() {
        try {
            Order cart = orderRepository.findFirstByStatusOrderByOrderIdDesc("CART");
            if (cart != null) {
                orderRepository.delete(cart);
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error("Error occurred while deleting cart: {}", e.getMessage());
            throw e;
        }
    }

    public List<Order> viewOrderHistory() {
        try {
            return orderRepository.findAll();
        } catch (Exception e) {
            logger.error("Error occurred while fetching order history: {}", e.getMessage());
            throw e;
        }
    }

    private void decrementAllProductStock() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            String url = "http://localhost:8080/product/decrementStock";
            restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);
        } catch (Exception e) {
            logger.error("Error occurred while decrementing product stock: {}", e.getMessage());
            throw e;
        }
    }
}
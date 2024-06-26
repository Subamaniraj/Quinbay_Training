package com.training.order.controller;
import com.training.order.entity.Order;
import com.training.order.entity.Product;
import com.training.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/getAllProducts")
    public List<Product> getAllProducts() {
        return orderService.getAllProducts();
    }

    @PostMapping(value = "/addToCart/{userId}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Void> addToCart(@PathVariable int userId) {
        orderService.addToCart(userId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/placeOrder")
    public ResponseEntity<Order> placeOrder() {
        Order order = orderService.placeOrder();
        if (order != null) {
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteCart")
    public ResponseEntity<Void> deleteCart() {
        boolean deleted = orderService.deleteCart();
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/orderHistory")
    public ResponseEntity<List<Order>> viewOrderHistory() {
        List<Order> orderHistory = orderService.viewOrderHistory();
        return new ResponseEntity<>(orderHistory, HttpStatus.OK);
    }
}
package com.training.order.service;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.training.order.entity.Order;
import com.training.order.entity.Product;
import com.training.order.utils.MongoDBConnection;
import org.bson.Document;
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

    @Autowired
    private RestTemplate restTemplate;

    private MongoCollection<Document> orderCollection = MongoDBConnection.getOrderCollection();

    public List<Product> getAllProducts() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        Product[] products = restTemplate.exchange("http://localhost:8080/product/getAllProducts", HttpMethod.GET, entity, Product[].class).getBody();
        return Arrays.asList(products);
    }

    public void addToCart(int userId) {
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

        Document doc = new Document("userId", cart.getUserId())
                .append("productList", productsToDocumentList(cart.getProductList()))
                .append("totalProducts", cart.getTotalProducts())
                .append("totalCost", cart.getTotalCost())
                .append("status", cart.getStatus());
        orderCollection.insertOne(doc);
    }

    public Order placeOrder() {
        Document doc = orderCollection.find(Filters.eq("status", "CART")).sort(new Document("_id", -1)).first();
        if (doc != null) {
            doc.put("status", "PLACED");
            orderCollection.replaceOne(Filters.eq("_id", doc.getObjectId("_id")), doc);
            decrementAllProductStock();
            return documentToOrder(doc);
        }
        return null;
    }

    public boolean deleteCart() {
        Document doc = orderCollection.find(Filters.eq("status", "CART")).sort(new Document("_id", -1)).first();
        if (doc != null) {
            orderCollection.deleteOne(Filters.eq("_id", doc.getObjectId("_id")));
            return true;
        }
        return false;
    }

    public List<Order> viewOrderHistory() {
        List<Order> orders = new ArrayList<>();
        for (Document doc : orderCollection.find()) {
            orders.add(documentToOrder(doc));
        }
        return orders;
    }

    private Order documentToOrder(Document doc) {
        Order order = new Order();
        order.setOrderId(doc.getObjectId("_id").toString());
        order.setUserId(doc.getInteger("userId"));
        order.setProductList(documentListToProducts((List<Document>) doc.get("productList")));
        order.setTotalProducts(doc.getInteger("totalProducts"));
        order.setTotalCost(doc.getDouble("totalCost"));
        order.setStatus(doc.getString("status"));
        return order;
    }

    private List<Document> productsToDocumentList(List<Product> products) {
        List<Document> documents = new ArrayList<>();
        for (Product product : products) {
            Document doc = new Document("id", product.getId())
                    .append("name", product.getName())
                    .append("stock", product.getStock())
                    .append("price", product.getPrice());
            documents.add(doc);
        }
        return documents;
    }

    private void decrementAllProductStock() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = "http://localhost:8080/product/decrementStock";
        restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);
    }

    private List<Product> documentListToProducts(List<Document> documents) {
        List<Product> products = new ArrayList<>();
        for (Document doc : documents) {
            Product product = new Product();
            product.setId(doc.getLong("id"));
            product.setName(doc.getString("name"));
            product.setStock(1);
            product.setPrice(doc.getDouble("price"));
            products.add(product);
        }
        return products;
    }
}
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
class Category {
    private int categoryId;
    private String categoryName;

    // Constructors
    public Category( int categoryId, String categoryName) {
        //this.id = new ObjectId();
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    // Getters and Setters
//    public ObjectId getId() {
//        return id;
//    }
//
//    public void setId(ObjectId id) {
//        this.id = id;
//    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    // toString Method
    @Override
    public String toString() {
        return "Category ID: " + categoryId + ", Category Name: " + categoryName;
    }
}
class Order {
    private int orderId;
    private int totalProducts;
    private double totalCost;

    // Constructor
    public Order(int orderId, int totalProducts, double totalCost) {
        this.orderId = orderId;
        this.totalProducts = totalProducts;
        this.totalCost = totalCost;
    }

    // Getters and Setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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
}
class OrderItem {
    private int orderItemId;
    private int orderId;
    private int productId;
    private double costPerProduct;

    // Constructor
    public OrderItem(int orderItemId, int orderId, int productId, double costPerProduct) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.productId = productId;
        this.costPerProduct = costPerProduct;
    }

    // Getters and Setters
    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getCostPerProduct() {
        return costPerProduct;
    }

    public void setCostPerProduct(double costPerProduct) {
        this.costPerProduct = costPerProduct;
    }
    public String displayInfo() {
        return "OrderItem ID: " + orderItemId + ", Order ID: " + orderId + ", Product ID: " + productId + ", Cost Per Product: " + costPerProduct;
    }
}


class Product {
    private static int counter = 0; // Static counter for productId
    private int productId;
    private String productName;
    private double productPrice;
    private int productStock;
    private boolean isDeleted;
    private Category category;

    // Constructor for adding new product
    public Product(String productName, double productPrice, int productStock, boolean isDeleted, Category category) {
        this.productId = ++counter; // Increment counter for new productId
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.isDeleted = isDeleted;
        this.category = category;
    }

    // Constructor for retrieving existing product from database
    public Product(int productId, String productName, double productPrice, int productStock, boolean isDeleted, Category category) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.isDeleted = isDeleted;
        this.category = category;
    }

    // Getters and setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductStock() {
        return productStock;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    // Display details method
    public String displayDetails() {
        return "Product ID: " + productId +
                "\nProduct Name: " + productName +
                "\nProduct Price: " + productPrice +
                "\nProduct Stock: " + productStock +
                "\nIs Deleted: " + isDeleted +
                "\nCategory: " + category.toString();
    }
}


//class Purchase {
//    private int purchaseId;
//    private int productId;
//    private int purchaseQuantity;
//
//    // Getters, setters, and constructors
//
//    public String displayInfo() {
//        return "Purchase ID: " + purchaseId + ", Product ID: " + productId + ", Quantity: " + purchaseQuantity;
//    }
//}


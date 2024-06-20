import java.io.*;
import java.util.*;

abstract class Product {
    public static int counter = 0;
    private int id;
    private String productName;
    private double productPrice;
    private int productStock;
    private boolean isDeleted;

    public Product(String productName, double productPrice, int productStock) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        id = ++counter;
        this.isDeleted = false;
    }

    public int getProductId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public int getProductStock() {
        return productStock;
    }

    public void setProductPrice(double price) {
        this.productPrice = price;
    }

    public void setProductStock(int stock) {
        this.productStock = stock;
    }

    public void reduceStock(int amount) {
        this.productStock -= amount;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public abstract String displayDetails();
}

class Purchase {
    public static int counter = 0;
    private int purchaseId;
    private int productId;
    private int purchaseQuantity;

    public Purchase(int productId, int quantity) {
        this.productId = productId;
        this.purchaseQuantity = quantity;
        purchaseId = ++counter;
    }

    public int getProductId() {
        return productId;
    }

    public int getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public int getPurchaseId() {
        return purchaseId;
    }

    public String displayInfo() {
        return "Purchase ID: " + purchaseId + ", Product ID: " + productId + ", Quantity: " + purchaseQuantity;
    }
}

class Book extends Product {
    public Book(String name, double price, int stock) {
        super(name, price, stock);
    }

    @Override
    public String displayDetails() {
        return "\nBook ID: " + getProductId() + "\n Name: " + getProductName() + "\n Price: " + getProductPrice() + "\n Stock: " + getProductStock();
    }
}

class Laptop extends Product {
    public Laptop(String name, double price, int stock) {
        super(name, price, stock);
    }

    @Override
    public String displayDetails() {
        return "\nLaptop ID: " + getProductId() + "\n Name: " + getProductName() + "\n Price: " + getProductPrice() + "\n Stock: " + getProductStock();
    }
}

class ProductUpdate {
    static Scanner sc = new Scanner(System.in);
    static Main m = new Main();

    static void updateStock() {
        synchronized (Main.products) {
            System.out.print("Enter the product ID: ");
            int id = sc.nextInt();
            Product product = m.findProduct(id);
            if (product != null && !product.isDeleted()) {
                System.out.println("Current stock: " + product.getProductStock());
                System.out.print("Choose update option:\n1. Replace\n2. Override\nEnter your choice: ");
                int option = sc.nextInt();
                System.out.print("Enter the new stock quantity: ");
                int stock = sc.nextInt();
                if (stock >= 0) {
                    switch (option) {
                        case 1:
                            product.setProductStock(stock);
                            break;
                        case 2:
                            product.setProductStock(product.getProductStock() + stock);
                            break;
                        default:
                            System.out.println("Invalid option.");
                            return;
                    }
                    System.out.println("Stock updated successfully.");
                    m.saveProducts();
                } else {
                    System.out.println("Enter valid stock.");
                }
            } else {
                System.out.println("Product not found.");
            }
        }
    }

    static void updatePrice() {
        synchronized (Main.products) {
            System.out.print("Enter product ID: ");
            int id = sc.nextInt();
            System.out.print("Enter new price: ");
            double price = sc.nextDouble();
            if (price >= 0) {
                Product product = m.findProduct(id);
                if (product != null && !product.isDeleted()) {
                    product.setProductPrice(price);
                    System.out.println("Price updated successfully.");
                    m.saveProducts();
                } else {
                    System.out.println("Product not found.");
                }
            } else {
                System.out.println("Enter valid price.");
            }
        }
    }
}

class ProductAdd {
    static Scanner sc = new Scanner(System.in);
    static Main m = new Main();

    static void addProduct() {
        System.out.print("Enter product type:\n1. Book\n2. Laptop\nChoice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter the product name: ");
        String name = sc.next();

        System.out.print("Enter the price of the product: ");
        double price = sc.nextDouble();

        if (price > 0) {
            System.out.print("Enter the product stock: ");
            int stock = sc.nextInt();

            if (stock > 0) {
                Product product;
                switch (choice) {
                    case 1:
                        product = new Book(name, price, stock);
                        break;
                    case 2:
                        product = new Laptop(name, price, stock);
                        break;
                    default:
                        System.out.println("Invalid product type choice.");
                        return;
                }
                m.products.add(product);
                System.out.println("Product added successfully!");
                m.saveProducts();
            } else {
                System.out.println("Stock cannot be a negative value.");
            }
        } else {
            System.out.println("Price cannot be a negative value.");
        }

    }
}

class ProductPurchase {
    static Scanner sc = new Scanner(System.in);
    static Main m = new Main();

    static void purchaseStock() {
        synchronized (Main.products) {
            synchronized (Main.purchases) {
                synchronized (Main.purchaseHistory) {
                    System.out.print("Enter product ID: ");
                    int id = sc.nextInt();
                    System.out.print("Enter the quantity: ");
                    int quantity = sc.nextInt();
                    if (quantity > 0) {
                        Product product = m.findProduct(id);
                        if (product != null && !product.isDeleted()) {
                            if (product.getProductStock() >= quantity) {
                                Purchase purchase = new Purchase(id, quantity);
                                m.purchases.add(purchase);
                                product.reduceStock(quantity);
                                int totalPurchased = m.purchaseHistory.getOrDefault(id, 0) + quantity;
                                m.purchaseHistory.put(id, totalPurchased);
                                System.out.println("Purchase successful.");
                                m.savePurchases();
                                m.saveProducts();
                            } else {
                                System.out.println("Insufficient stock.");
                            }
                        } else {
                            System.out.println("Product not found.");
                        }
                    } else {
                        System.out.println("Enter valid quantity.");
                    }
                }
            }
        }
    }
}

class ProductDelete {
    static Scanner sc = new Scanner(System.in);
    static Main m = new Main();

    static void deleteProduct() {
        synchronized (Main.products) {
            System.out.print("Enter the product ID to delete: ");
            int id = sc.nextInt();
            Product product = m.findProduct(id);
            if (product != null) {
                if (!m.purchaseHistory.containsKey(id)) {
                    product.setDeleted(true);
                    System.out.println("Product deleted successfully.");
                    m.saveProducts();
                } else {
                    System.out.println("Cannot delete product. It has purchase history.");
                }
            } else {
                System.out.println("Product not found.");
            }
        }
    }
}

public class Main {
    static ArrayList<Product> products = new ArrayList<>();
    static ArrayList<Purchase> purchases = new ArrayList<>();
    static Map<Integer, Integer> purchaseHistory = new HashMap<>();
    static Scanner sc = new Scanner(System.in);
    static String productsFilePath = "/Users/subashreem/IdeaProjects/Training_day2/src/products.txt";
    static String purchasesFilePath = "/Users/subashreem/IdeaProjects/Training_day2/src/purchases.txt";

    public static void main(String[] args) {
        loadProducts();
        loadPurchases();
        ProductUpdate update = new ProductUpdate();
        ProductDelete delete = new ProductDelete();

        boolean exit = false;
        while (!exit) {
            try {
                System.out.println("1. Add Product\n2. View Product by ID\n3. View All Products\n4. Update Stock\n5. Update Price\n6. Purchase Stock\n7. List Purchase History\n8. Delete Product\n9. Exit");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        Thread addProductThread = new Thread(new AddProductTask());
                        addProductThread.start();
                        addProductThread.join();
                        break;
                    case 2:
                        viewProduct();
                        break;
                    case 3:
                        viewAllProducts();
                        break;
                    case 4:
                        Thread updateStockThread = new Thread(new UpdateStockTask());
                        updateStockThread.start();
                        updateStockThread.join();
                        break;
                    case 5:
                        Thread updatePriceThread = new Thread(new UpdatePriceTask());
                        updatePriceThread.start();
                        updatePriceThread.join();
                        break;
                    case 6:
                        Thread purchaseStockThread = new Thread(new PurchaseStockTask());
                        purchaseStockThread.start();
                        purchaseStockThread.join();
                        break;
                    case 7:
                        listPurchaseHistory();
                        break;
                    case 8:
                        delete.deleteProduct();
                        break;
                    case 9:
                        saveProducts();
                        savePurchases();
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (InputMismatchException | InterruptedException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                sc.next();
            }
        }
    }

    static void viewProduct() {
        synchronized (Main.products) {
            System.out.print("Enter the product ID: ");
            int id = sc.nextInt();
            Product product = findProduct(id);
            if (product != null && !product.isDeleted()) {
                System.out.println(product.displayDetails());
            } else {
                System.out.println("Product not found.");
            }
        }
    }

    static void viewAllProducts() {
        synchronized (Main.products) {
            if (products.isEmpty()) {
                System.out.println("No products available.");
            } else {
                for (Product product : products) {
                    if (!product.isDeleted()) {
                        System.out.println(product.displayDetails());
                    }
                }
            }
        }
    }

    static void listPurchaseHistory() {
        synchronized (Main.purchaseHistory) {
            if (purchaseHistory.isEmpty()) {
                System.out.println("No purchase history available.");
            } else {
                System.out.println("Purchase History:");
                for (Map.Entry<Integer, Integer> entry : purchaseHistory.entrySet()) {
                    int productId = entry.getKey();
                    int totalPurchased = entry.getValue();
                    System.out.println("Product ID: " + productId + ", Total Purchased: " + totalPurchased);
                }
            }
        }
    }

    static Product findProduct(int id) {
        synchronized (Main.products) {
            for (Product product : products) {
                if (product.getProductId() == id && !product.isDeleted()) {
                    return product;
                }
            }
            return null;
        }
    }

    static void loadProducts() {
        synchronized (Main.products) {
            try (BufferedReader br = new BufferedReader(new FileReader(productsFilePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    int id = Integer.parseInt(parts[0]);
                    String type = parts[1];
                    String name = parts[2];
                    double price = Double.parseDouble(parts[3]);
                    int stock = Integer.parseInt(parts[4]);
                    boolean isDeleted = Boolean.parseBoolean(parts[5]);
                    Product product;
                    if (type.equals("Book")) {
                        product = new Book(name, price, stock);
                    } else {
                        product = new Laptop(name, price, stock);
                    }
                    product.setDeleted(isDeleted);
                    Product.counter = Math.max(Product.counter, id);
                    products.add(product);
                }
            } catch (IOException e) {
                System.out.println("Error loading products: " + e.getMessage());
            }
        }
    }

    static void saveProducts() {
        synchronized (Main.products) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(productsFilePath))) {
                for (Product product : products) {
                    bw.write(product.getProductId() + "," + (product instanceof Book ? "Book" : "Laptop") + "," + product.getProductName() + "," + product.getProductPrice() + "," + product.getProductStock() + "," + product.isDeleted());
                    bw.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error saving products: " + e.getMessage());
            }
        }
    }

    static void loadPurchases() {
        synchronized (Main.purchases) {
            synchronized (Main.purchaseHistory) {
                try (BufferedReader br = new BufferedReader(new FileReader(purchasesFilePath))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] parts = line.split(",");
                        int purchaseId = Integer.parseInt(parts[0]);
                        int productId = Integer.parseInt(parts[1]);
                        int quantity = Integer.parseInt(parts[2]);
                        Purchase purchase = new Purchase(productId, quantity);
                        purchaseHistory.put(productId, purchaseHistory.getOrDefault(productId, 0) + quantity);
                        Purchase.counter = Math.max(Purchase.counter, purchaseId);
                        purchases.add(purchase);
                    }
                } catch (IOException e) {
                    System.out.println("Error loading purchases: " + e.getMessage());
                }
            }
        }
    }

    static void savePurchases() {
        synchronized (Main.purchases) {
            synchronized (Main.purchaseHistory) {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(purchasesFilePath))) {
                    for (Purchase purchase : purchases) {
                        bw.write(purchase.getPurchaseId() + "," + purchase.getProductId() + "," + purchase.getPurchaseQuantity());
                        bw.newLine();
                    }
                } catch (IOException e) {
                    System.out.println("Error saving purchases: " + e.getMessage());
                }
            }
        }
    }

    static class AddProductTask implements Runnable {
        @Override
        public void run() {
            ProductAdd.addProduct();
        }
    }

    static class UpdateStockTask implements Runnable {
        @Override
        public void run() {
            ProductUpdate.updateStock();
        }
    }

    static class UpdatePriceTask implements Runnable {
        @Override
        public void run() {
            ProductUpdate.updatePrice();
        }
    }

    static class PurchaseStockTask implements Runnable {
        @Override
        public void run() {
            ProductPurchase.purchaseStock();
        }
    }
}

import java.io.*;
import java.util.*;

abstract class Product {
    public static int counter = 0;
    private int id;
    private String name;
    private double price;
    private int stock;
    private boolean isDeleted;

    public Product(String name, double price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        id = ++counter;
        this.isDeleted = false;
    }

    public int getProductId() {
        return id;
    }

    public String getProductName() {
        return name;
    }

    public double getProductPrice() {
        return price;
    }

    public int getProductStock() {
        return stock;
    }

    public void setProductPrice(double price) {
        this.price = price;
    }

    public void setProductStock(int stock) {
        this.stock = stock;
    }

    public void reduceStock(int amount) {
        this.stock -= amount;
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
    private int quantity;

    public Purchase(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
        purchaseId = ++counter;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPurchaseId() {
        return purchaseId;
    }

    public String displayInfo() {
        return "Purchase ID: " + purchaseId + ", Product ID: " + productId + ", Quantity: " + quantity;
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

class ProductUpdate{
    static Scanner sc=new Scanner(System.in);
    static Main m=new Main();
    static void updateStock() {
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
            }
            else {
                System.out.println("Enter valid stock.");
            }
        }else {
                System.out.println("Product not found.");
            }
        }

    static void updatePrice() {
        System.out.print("Enter product ID: ");
        int id = sc.nextInt();
        System.out.print("Enter new price: ");
        double price = sc.nextDouble();
        if(price>=0) {
            Product product = m.findProduct(id);
            if (product != null && !product.isDeleted()) {
                product.setProductPrice(price);
                System.out.println("Price updated successfully.");
                m.saveProducts();
            } else {
                System.out.println("Product not found.");
            }
        }
        else {
            System.out.println("Enter the valid price.");
        }
    }

}

class ProductDelete {
    static Scanner sc=new Scanner(System.in);
    static Main m=new Main();
    static void deletePurchaseHistory() {
        m.purchases.clear();
        m.purchaseHistory.clear();
        System.out.println("All purchase history deleted.");
        m.savePurchases();
    }

    static void deletePurchaseHistoryById() {
        System.out.print("Enter the product ID to delete: ");
        int productId = sc.nextInt();
        m.purchases.removeIf(purchase -> purchase.getProductId() == productId);
        m.purchaseHistory.remove(productId);
        System.out.println("Purchase history for product ID " + productId + " deleted.");
        m.savePurchases();
    }

    static void deleteProduct() {
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

public class Main {
    static ArrayList<Product> products = new ArrayList<>();
    static ArrayList<Purchase> purchases = new ArrayList<>();
    static Map<Integer, Integer> purchaseHistory = new HashMap<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        loadProducts();
        loadPurchases();
        ProductUpdate update = new ProductUpdate();
        ProductDelete delete = new ProductDelete();

        boolean exit = false;
        while (!exit) {
            try {
                System.out.println("1. Add Product\n2. View Product by ID\n3. View All Products\n4. Update Stock\n5. Update Price\n6. Purchase Stock\n7. List Purchase History\n8. Delete Purchase History\n9. Delete Purchase History By Id\n10. Delete Product\n11. Exit");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        addProduct();
                        break;
                    case 2:
                        viewProduct();
                        break;
                    case 3:
                        viewAllProducts();
                        break;
                    case 4:
                        update.updateStock();
                        break;
                    case 5:
                        update.updatePrice();
                        break;
                    case 6:
                        purchaseStock();
                        break;
                    case 7:
                        listPurchaseHistory();
                        break;
                    case 8:
                        delete.deletePurchaseHistory();
                        break;
                    case 9:
                        delete.deletePurchaseHistoryById();
                        break;
                    case 10:
                        delete.deleteProduct();
                        break;
                    case 11:
                        saveProducts();
                        savePurchases();
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                sc.next();
            }
        }
    }

    static void addProduct() {
        System.out.print("Enter product type \n1. Book \n2. Laptop ");
        int type = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter the product name: ");
        String name = sc.nextLine();
        System.out.print("Enter the price of the product: ");
        double price = sc.nextDouble();
        if(price>0) {
            System.out.print("Enter the product stock: ");
            int stock = sc.nextInt();
            if(stock>0) {
                Product product;
                if (type == 1) {
                    product = new Book(name, price, stock);
                } else {
                    product = new Laptop(name, price, stock);
                }
                products.add(product);
                System.out.println("Product added successfully!!!");
                saveProducts();
            } else {
                System.out.println(("Stock cannot be a negative value."));
            }
        }
        else {
            System.out.println("Price cannot be a negative value.");
        }
    }

    static void viewProduct() {
        System.out.print("Enter the product ID: ");
        int id = sc.nextInt();
        Product product = findProduct(id);
        if (product != null && !product.isDeleted()) {
            System.out.println(product.displayDetails());
        } else {
            System.out.println("Product not found.");
        }
    }

    static void viewAllProducts() {
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

    static void purchaseStock() {
        System.out.print("Enter product ID: ");
        int id = sc.nextInt();
        System.out.print("Enter the quantity: ");
        int quantity = sc.nextInt();
        if(quantity>0) {
            Product product = findProduct(id);
            if (product != null && !product.isDeleted()) {
                if (product.getProductStock() >= quantity) {
                    product.reduceStock(quantity);
                    Purchase purchase = new Purchase(id, quantity);
                    purchases.add(purchase);
                    purchaseHistory.put(id, purchaseHistory.getOrDefault(id, 0) + quantity);
                    System.out.println("Product purchased successfully.");
                    saveProducts();
                    savePurchases();
                } else {
                    System.out.println("Stock is insufficient.");
                }
            } else {
                System.out.println("Product not found.");
            }
        }
        else {
            System.out.println(("Enter the valid quantity."));
        }
    }

    static void listPurchaseHistory() {
        if (purchases.isEmpty()) {
            System.out.println("No purchase history available.");
        } else {
            for (Purchase purchase : purchases) {
                System.out.println(purchase.displayInfo());
            }
        }
    }


    static Product findProduct(int id) {
        for (Product product : products) {
            if (product.getProductId() == id) {
                return product;
            }
        }
        return null;
    }

    static void loadProducts() {
        String filePath = "/Users/subashreem/IdeaProjects/Training_day2/src/products.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
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
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    static void saveProducts() {
        String filePath = "/Users/subashreem/IdeaProjects/Training_day2/src/products.txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Product product : products) {
                bw.write(product.getProductId() + "," + (product instanceof Book ? "Book" : "Laptop") + "," + product.getProductName() + "," + product.getProductPrice() + "," + product.getProductStock() + "," + product.isDeleted());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving products: " + e.getMessage());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    static void loadPurchases() {
        String filePath = "/Users/subashreem/IdeaProjects/Training_day2/src/purchases.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
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
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    static void savePurchases() {
        String filePath = "/Users/subashreem/IdeaProjects/Training_day2/src/purchases.txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Purchase purchase : purchases) {
                bw.write(purchase.getPurchaseId() + "," + purchase.getProductId() + "," + purchase.getQuantity());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving purchases: " + e.getMessage());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

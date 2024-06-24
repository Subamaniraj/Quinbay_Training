package org.training.day4;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class ProductAdd {
    static ProductDAO productDAO = new ProductDAO();

    public static void addProduct() {
        Scanner sc = new Scanner(System.in);

        int productId = 0;


        System.out.print("Enter Product Name: ");
        String productName = sc.nextLine();

        System.out.print("Enter Product Price: ");
        double productPrice = sc.nextDouble();

        System.out.print("Enter Product Stock: ");
        int productStock = sc.nextInt();
        sc.nextLine(); // consume newline

        System.out.print("Enter Category ID: ");
        int categoryId = sc.nextInt();
        sc.nextLine(); // consume newline

        // Assuming CategoryDAO is already defined and available
        CategoryDAO categoryDAO = new CategoryDAO();
        Category category = categoryDAO.findCategory(categoryId);
        if (category == null) {
            System.out.println("Category not found.");
            return;
        }


        Product product = new Product(productName, productPrice, productStock, false, category);

        productDAO.addProduct(product);
        System.out.println("Product added successfully.");
        updateExcel(product);
    }
    private static void updateExcel(Product product) {
        String excelFilePath = "/Users/subashreem/Downloads/Training3/src/main/java/org/training/day4/products.xlsx";
        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            Row newRow = sheet.createRow(lastRowNum + 1);

            Cell cell = newRow.createCell(0);
            cell.setCellValue(product.getProductId());

            cell = newRow.createCell(1);
            cell.setCellValue(product.getProductName());

            cell = newRow.createCell(2);
            cell.setCellValue(product.getProductPrice());

            cell = newRow.createCell(3);
            cell.setCellValue(product.getProductStock());

            cell = newRow.createCell(4);
            cell.setCellValue(product.getCategory().getCategoryId());

            try (FileOutputStream fos = new FileOutputStream(excelFilePath)) {
                workbook.write(fos);
            }
        } catch (IOException e) {
            System.out.println("Error updating Excel file: " + e.getMessage());
        }
    }
}



class ProductUpdate {
    static ProductDAO productDAO = new ProductDAO();

    public static void updateStock(int productId, int newStock) {
        Product product = productDAO.findProduct(productId);
        if (product != null) {
            product.setProductStock(newStock);
            productDAO.updateProduct(product);
            System.out.println("Stock updated successfully.");
        } else {
            System.out.println("Product not found.");
        }
    }

    public static void updatePrice(int productId, double newPrice) {
        Product product = productDAO.findProduct(productId);
        if (product != null) {
            product.setProductPrice(newPrice);
            productDAO.updateProduct(product);
            System.out.println("Price updated successfully.");
        } else {
            System.out.println("Product not found.");
        }
    }
}

class PurchaseStock {
    static OrderDAO orderDAO;

    static {
        try {
            orderDAO = new OrderDAO();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void purchase() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Product ID: ");
        int productId = sc.nextInt();

        System.out.print("Enter Purchase Quantity: ");
        int purchaseQuantity = sc.nextInt();

        double productCost = 0;
        Product product = ProductDAO.findProduct(productId);
        if (product != null) {
            productCost = product.getProductPrice();
            int newStock = product.getProductStock() - purchaseQuantity;
            ProductUpdate.updateStock(productId, newStock);
        } else {
            System.out.println("Product not found.");
            return;
        }

        // Generate unique order ID (this is an example, adjust as necessary)
        int orderId = generateOrderId();

        Order order = new Order(orderId, purchaseQuantity, productCost * purchaseQuantity);
        try {
            orderDAO.addOrder(order);

            // Generate unique order item ID (this is an example, adjust as necessary)
            int orderItemId = generateOrderItemId();

            OrderItem orderItem = new OrderItem(orderItemId, orderId, productId, productCost);
            orderDAO.addOrderItem(orderItem);

            System.out.println("Purchase recorded successfully.");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static int generateOrderId() {
        // Implement logic to generate or retrieve a unique order ID
        return new Random().nextInt(10000); // Example: random order ID for demonstration
    }

    private static int generateOrderItemId() {
        // Implement logic to generate or retrieve a unique order item ID
        return new Random().nextInt(10000); // Example: random order item ID for demonstration
    }
}

class PurchaseHistory {
    static OrderDAO orderDAO;

    static {
        try {
            orderDAO = new OrderDAO();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void list(int productId) {
        try {
            List<OrderItem> orderItems = orderDAO.getOrderItemsByProductId(productId);
            if (orderItems.isEmpty()) {
                System.out.println("No purchase history found for the given product ID.");
            } else {
                for (OrderItem orderItem : orderItems) {
                    System.out.println(orderItem.displayInfo());
                }
            }
        } catch (Exception e) {
            System.out.println("Error fetching purchase history: " + e.getMessage());
        }
    }
}

class ProductDelete {
    static ProductDAO productDAO = new ProductDAO();

    public static void deleteProduct(int productId) {
        productDAO.deleteProduct(productId);
        System.out.println("Product deleted successfully.");
    }
}
class CategoryAdd {
    static CategoryDAO categoryDAO = new CategoryDAO();

    public static void addCategory() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Category ID: ");
        int categoryId = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Category Name: ");
        String categoryName = sc.nextLine();


        Category category = new Category( categoryId, categoryName);
        categoryDAO.addCategory(category);
        System.out.println("Category added successfully.");
    }
}
class CategoryUpdate {
    static CategoryDAO categoryDAO = new CategoryDAO();

    public static void updateCategory() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Category ID: ");
        int categoryId = sc.nextInt();
        sc.nextLine(); // consume newline

        System.out.print("Enter New Category Name: ");
        String newCategoryName = sc.nextLine();

        Category category = categoryDAO.findCategory(categoryId);
        if (category != null) {
            category.setCategoryName(newCategoryName);
            categoryDAO.updateCategory(category);
            System.out.println("Category updated successfully.");
        } else {
            System.out.println("Category not found.");
        }
    }
}

class CategoryDelete {
    static CategoryDAO categoryDAO = new CategoryDAO();

    public static void deleteCategory(int categoryId) {
        categoryDAO.deleteCategory(categoryId);
        System.out.println("Category deleted successfully.");
    }
}


class PurchaseDelete {
    static OrderDAO orderDAO;

    static {
        try {
            orderDAO = new OrderDAO();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deletePurchase(int orderId) throws SQLException {
        orderDAO.deleteOrderItems(orderId);
        orderDAO.deleteOrder(orderId);
        System.out.println("Purchase deleted successfully.");
    }
}




public class Main {
    static Scanner sc = new Scanner(System.in);
    static CategoryDAO categoryDAO = new CategoryDAO();
    static ProductDAO productDAO = new ProductDAO();
    static OrderDAO orderDAO;


    static {
        try {
            orderDAO = new OrderDAO();
        } catch (SQLException e) {
            System.err.println("Failed to initialize OrderDAO: " + e.getMessage());
            orderDAO = null;
        }
    }

    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nProduct Management System Menu:");
            System.out.println("1. Add Product");
            System.out.println("2. View Product by ID");
            System.out.println("3. View All Products");
            System.out.println("4. Update Stock");
            System.out.println("5. Update Price");
            System.out.println("6. Purchase Stock");
            System.out.println("7. List Purchase History");
            System.out.println("8. Delete Product");
            System.out.println("9. Add Category");
            System.out.println("10. Update Category");
            System.out.println("11. Delete Category");
            System.out.println("12. Delete Purchase History");
            System.out.println("13. Exit");

            try {
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        ProductAdd.addProduct();
                        break;
                    case 2:
                        System.out.print("Enter Product ID: ");
                        int productId = sc.nextInt();
                        Product product = productDAO.findProduct(productId);
                        if (product != null) {
                            System.out.println(product.displayDetails());
                        } else {
                            System.out.println("Product not found.");
                        }
                        break;
                    case 3:
                        List<Product> products = productDAO.getAllProducts();
                        for (Product prod : products) {
                            System.out.println(prod.displayDetails());
                        }
                        break;
                    case 4:
                        System.out.print("Enter Product ID: ");
                        productId = sc.nextInt();
                        System.out.print("Enter New Stock: ");
                        int newStock = sc.nextInt();
                        ProductUpdate.updateStock(productId, newStock);
                        break;
                    case 5:
                        System.out.print("Enter Product ID: ");
                        productId = sc.nextInt();
                        System.out.print("Enter New Price: ");
                        double newPrice = sc.nextDouble();
                        ProductUpdate.updatePrice(productId, newPrice);
                        break;
                    case 6:
                        PurchaseStock.purchase();
                        break;
                    case 7:
                        System.out.print("Enter Product ID: ");
                        productId = sc.nextInt();
                        PurchaseHistory.list(productId);
                        break;
                    case 8:
                        System.out.print("Enter Product ID: ");
                        productId = sc.nextInt();
                        ProductDelete.deleteProduct(productId);
                        break;
                    case 9:
                        CategoryAdd.addCategory();
                        break;
                    case 10:
                        CategoryUpdate.updateCategory();
                        break;
                    case 11:
                        System.out.print("Enter Category ID: ");
                        int categoryId = sc.nextInt();
                        CategoryDelete.deleteCategory(categoryId);
                        break;
                    case 12:
                        System.out.print("Enter Purchase ID: ");
                        int purchaseId = sc.nextInt();
                        PurchaseDelete.deletePurchase(purchaseId);
                        break;
                    case 13:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                sc.nextLine(); // clear the buffer
            }
        }
    }
}

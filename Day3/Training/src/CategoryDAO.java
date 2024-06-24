import com.mongodb.client.MongoClient;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private static MongoCollection<Document> collection;

    public CategoryDAO() {
        MongoDatabase database = DatabaseConnection.getMongoDatabase();
        this.collection = database.getCollection("categories");
    }

    public void addCategory(Category category) {
        Document doc = new Document("categoryId", category.getCategoryId())
                .append("categoryName", category.getCategoryName());
        collection.insertOne(doc);
    }

    public void updateCategory(Category category) {
        Document doc = new Document("categoryId", category.getCategoryId())
                .append("categoryName", category.getCategoryName());
        collection.replaceOne(eq("categoryId", category.getCategoryId()), doc);
    }

    public void deleteCategory(int categoryId) {
        collection.deleteOne(eq("categoryId", categoryId));
    }

    public Category findCategory(int categoryId) {
        Document doc = collection.find(eq("categoryId", categoryId)).first();
        if (doc != null) {
            return new Category(doc.getInteger("categoryId"), doc.getString("categoryName"));
        }
        return null;
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        collection.find().forEach(doc -> {
            Category category = new Category(doc.getInteger("categoryId"), doc.getString("categoryName"));
            categories.add(category);
        });
        return categories;
    }
}

class ProductDAO {
    private static MongoCollection<Document> collection;

    public ProductDAO() {
        MongoDatabase database = DatabaseConnection.getMongoDatabase();
        this.collection = database.getCollection("products");
    }

    public void addProduct(Product product) {
        Document doc = new Document("productId", product.getProductId())
                .append("productName", product.getProductName())
                .append("productPrice", product.getProductPrice())
                .append("productStock", product.getProductStock())
                .append("deleted", product.isDeleted())
                .append("category", new Document("categoryId", product.getCategory().getCategoryId())
                        .append("categoryName", product.getCategory().getCategoryName()));
        collection.insertOne(doc);
    }

    public void updateProduct(Product product) {
        Document doc = new Document("productId", product.getProductId())
                .append("productName", product.getProductName())
                .append("productPrice", product.getProductPrice())
                .append("productStock", product.getProductStock())
                .append("deleted", product.isDeleted())
                .append("category", new Document("categoryId", product.getCategory().getCategoryId())
                        .append("categoryName", product.getCategory().getCategoryName()));
        collection.replaceOne(eq("productId", product.getProductId()), doc);
    }

    public void deleteProduct(int productId) {
        collection.deleteOne(eq("productId", productId));
    }

    public static Product findProduct(int productId) {
        Document doc = collection.find(eq("productId", productId)).first();
        if (doc != null) {
            Document categoryDoc = (Document) doc.get("category");
            Category category = new Category(categoryDoc.getInteger("categoryId"),
                    categoryDoc.getString("categoryName"));
            return new Product(doc.getInteger("productId"),
                    doc.getString("productName"),
                    doc.getDouble("productPrice"),
                    doc.getInteger("productStock"),
                    doc.getBoolean("deleted"),
                    category);
        }
        return null;
    }
//    public int getMaxProductId() {
//        FindIterable<Document> documents = collection.find().sort(descending("productId")).limit(1);
//        if (documents.iterator().hasNext()) {
//            Document document = documents.iterator().next();
//            return document.getInteger("productId");
//        }
//        return 0; // Return 0 if no product found
   // }
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        collection.find().forEach(doc -> {
            Document categoryDoc = (Document) doc.get("category");
            Category category = new Category(categoryDoc.getInteger("categoryId"),
                    categoryDoc.getString("categoryName"));
            Product product = new Product(doc.getInteger("productId"),
                    doc.getString("productName"),
                    doc.getDouble("productPrice"),
                    doc.getInteger("productStock"),
                    doc.getBoolean("deleted"),
                    category);
            products.add(product);
        });
        return products;
    }
}

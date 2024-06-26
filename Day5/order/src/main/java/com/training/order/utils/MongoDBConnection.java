package com.training.order.utils;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDBConnection {

    private static final String URI = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "orderDB";
    private static final String COLLECTION_NAME = "orders";

    private static MongoClient mongoClient = MongoClients.create(URI);
    private static MongoDatabase mongoDatabase = mongoClient.getDatabase(DATABASE_NAME);

    public static MongoCollection<Document> getOrderCollection() {
        return mongoDatabase.getCollection(COLLECTION_NAME);
    }
}
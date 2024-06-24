import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class DatabaseConnection {
    private static final String MONGO_DB_URI = "mongodb://localhost:27017";
    private static final String MONGO_DB_NAME = "product_management";
    private static final String POSTGRES_DB_URL = "jdbc:postgresql://localhost:5432/product_management";
    private static final String POSTGRES_DB_USER = "subashreem";
    private static final String POSTGRES_DB_PASSWORD = "Suba_2004";

    private static MongoClient mongoClient;
    private static Connection postgresConnection;

    public static MongoDatabase getMongoDatabase() {

        if (mongoClient == null) {
            CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
            CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                    fromProviders(pojoCodecProvider));

            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(new com.mongodb.ConnectionString(MONGO_DB_URI))
                    .codecRegistry(pojoCodecRegistry)
                    .build();

            mongoClient = MongoClients.create(settings);
        }
        return mongoClient.getDatabase(MONGO_DB_NAME);
    }

    public static Connection getPostgresConnection() throws SQLException {
        if (postgresConnection == null) {
            postgresConnection = DriverManager.getConnection(POSTGRES_DB_URL, POSTGRES_DB_USER, POSTGRES_DB_PASSWORD);
        }
        return postgresConnection;
    }
}

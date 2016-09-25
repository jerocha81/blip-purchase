package pt.blip.blipmongolib;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

import pt.blip.blipmongolib.exception.MongoException;

/**
 * Mongo class
 * 
 * @author Jose Rocha
 */
public class Mongo {
    private MongoDatabase database;
    private MongoClient mongoClient;
    private Map<String, MongoCollection<Document>> mapDataCollection;
    private UpdateOptions updateOptions;

    public Mongo(String uri, String name) throws MongoException {
        MongoClientURI mongoClientUri = new MongoClientURI(uri, MongoClientOptions.builder().socketKeepAlive(true));
        this.mongoClient = new MongoClient(mongoClientUri);
        this.database = mongoClient.getDatabase(name);

        // create new or update an existing document
        this.updateOptions = new UpdateOptions();
        this.updateOptions.upsert(true);
    }

    /**
     * Get Mongo collection
     * 
     * @param collection
     *            the Mongo collection
     * @return
     */
    public MongoCollection<Document> getMongoCollection(String collection) {
        MongoCollection<Document> mongoCollection = null;
        this.mapDataCollection = new ConcurrentHashMap<String, MongoCollection<Document>>();
        if (this.mapDataCollection.containsKey(collection)) {
            mongoCollection = this.mapDataCollection.get(collection);
        } else {
            if (this.database != null) {
                mongoCollection = this.database.getCollection(collection);
                this.mapDataCollection.put(collection, mongoCollection);
            }
        }

        return mongoCollection;
    }

    /**
     * Find
     * 
     * @param collection
     *            the Mongo collection
     * @param filter
     *            the query filter
     * @return
     * @throws MongoException
     */
    public FindIterable<Document> find(String collection, Bson filter) throws MongoException {
        MongoCollection<Document> mongoCollection = this.getMongoCollection(collection);
        if (mongoCollection == null) {
            throw new MongoException("The collection '" + collection + "' doesn't exist");
        }
        return mongoCollection.find(filter);
    }

    /**
     * Insert or Update a document
     * 
     * @param collection
     * @param filter
     * @param document
     * @throws MongoException
     */
    public void insertOrUpdateDocument(String collection, Bson filter, Document document) throws MongoException {
        MongoCollection<Document> mongoCollection = this.getMongoCollection(collection);
        if (mongoCollection == null) {
            throw new MongoException("The collection '" + collection + "' doesn't exist");
        }

        try {
            mongoCollection.updateOne(filter, new BasicDBObject("$set", document), this.updateOptions);
        } catch (Exception e) {
            throw new MongoException("Could not insert or update the document");
        }
    }

    /**
     * Close
     * 
     * @throws MongoException
     */
    public void close() throws MongoException {
        try {
            if (this.mongoClient != null) {
                this.mongoClient.close();
            }
        } catch (Exception e) {
            throw new MongoException("Error closing MongoDB connection: ", e);
        } finally {
            this.mapDataCollection = null;
            this.mongoClient = null;
        }
    }
}

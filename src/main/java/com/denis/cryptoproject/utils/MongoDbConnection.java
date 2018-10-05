package com.denis.cryptoproject.utils;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;

import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class MongoDbConnection {

    public MongoClient mongoClient = new MongoClient("localhost", 27017);

    public MongoDatabase db = mongoClient.getDatabase("CryptoDB");
    public MongoCollection<Document> collection = db.getCollection("collectionOfCurrencies");

        public MongoDbConnection() {
        }

    public void insertNewItem (List <Document> docs){
            if (!docs.isEmpty()) {
                try {
                    collection.insertMany(docs);
                }
                catch (NullPointerException ignored){}
                finally {
                    System.out.println(docs.size() + " have been added to DataBase");
                }
            }
    }

    public void removeRecords (String toDelete){
        DeleteResult deleteResult = collection.deleteMany(eq("TimeStamp", toDelete));
        System.out.println(deleteResult.getDeletedCount() + " records have been removed");
    }
}
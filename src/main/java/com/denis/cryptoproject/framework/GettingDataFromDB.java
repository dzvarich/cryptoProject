package com.denis.cryptoproject.framework;

import com.denis.cryptoproject.utils.MongoDbConnection;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.Block;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.and;

public class GettingDataFromDB {


    private MongoDbConnection mongoDbConnection = new MongoDbConnection();

    public String getTodayToString(int days, int minutes){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Calendar currentDate = Calendar.getInstance();
        currentDate.add(Calendar.MINUTE, minutes);
        currentDate.add(Calendar.DATE, days);
        dateFormat.setCalendar(currentDate);
        return dateFormat.format(currentDate.getTime());
    }

    public HashMap<String, String> getData (List<String> list, String day){
        HashMap <String, String> result = new HashMap<>();
        for (String currency: list
        ) {
            if (currency != null) {
                try (MongoCursor<Document> cursor = mongoDbConnection.collection
                        .find(and(eq("MarketName", "BTC-"+currency.trim()), eq("TimeStamp",day)))
                        .iterator()) {
                    while (cursor.hasNext()) {
                        result.put(currency.trim(), cursor.next().toJson());
                    }
                }
            }
        }
        return result;
    }

    public String getValueOfNode(String json) throws IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);
            return root.path("Ask").asText();
        }
        catch (NullPointerException n){
            System.out.println("Node is empty");
            return "";
        }
    }

    public void findSpecific (){
        Block<Document> printBlock = new Block<Document>() {
            @Override
            public void apply(final Document document) {
                try {
                    JsonNode node = new ObjectMapper().readTree(document.toJson());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println(document.toJson());
            }
        };

        mongoDbConnection.collection.find().forEach(printBlock);
    }

    public void findAll () throws IOException {
        MongoCursor<Document> cursor = mongoDbConnection.collection.find().iterator();
        Set <String> set = new HashSet<>();
        try {
            while (cursor.hasNext()) {
                JsonNode node = new ObjectMapper().readTree(cursor.next().toJson());
                set.add(node.get("TimeStamp").asText());
            }
        } finally {
            cursor.close();
        }
        for (String timestamp: set
             ) {
            System.out.println(timestamp);
        }
    }
}
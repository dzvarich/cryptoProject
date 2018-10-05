package com.denis.cryptoproject.framework;

import com.denis.cryptoproject.utils.MongoDbConnection;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.Block;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


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

    public String getYesterdayToString(){
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        dateFormat.setCalendar(yesterday);
        return dateFormat.format(yesterday.getTime());
    }

    public String getDayToDelete(){
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -1);
        yesterday.add(Calendar.MINUTE, -1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        dateFormat.setCalendar(yesterday);
        return dateFormat.format(yesterday.getTime());
    }

    public void findAndShowAll (){
        try (MongoCursor<Document> cursor = mongoDbConnection.collection.find().iterator()) {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        }
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
                System.out.println(document.toJson());
            }
        };

        mongoDbConnection.collection.find(eq("TimeStamp", "2018-10-04T20:06")).forEach(printBlock);
    }
}
package com.denis.cryptoproject.framework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.Document;


public class DocumentGenerator extends BaseTest {

    public String getJsonFromAPI (String link) throws IOException {
        URL yahoo = new URL(link);
        URLConnection yc = yahoo.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        yc.getInputStream()));
        String inputLine = in.readLine();
        in.close();
        return inputLine;
    }

    public List<Document> createListOfDocs (String text) throws IOException, ParseException {

        JsonNode node = new ObjectMapper().readTree(text).get("result");
        List<Document> listOfDocs = new ArrayList<>();
        for (JsonNode currentNode: node
             ) {
            if (currentNode.get("MarketName").asText().startsWith("BTC")) {
                Document doc = new Document("MarketName", currentNode.get("MarketName").asText())
                        .append("High", currentNode.get("High").asDouble())
                        .append("Low", currentNode.get("Low").asDouble())
                        .append("Volume", currentNode.get("Volume").asText())
                        .append("Last", currentNode.get("Last").asDouble())
                        .append("BaseVolume", currentNode.get("BaseVolume").asText())
                        .append("TimeStamp", gettingDataFromDB.getTodayToString(0,0))
                        .append("Bid", currentNode.get("Bid").asDouble())
                        .append("Ask", currentNode.get("Ask").asDouble())
                        .append("OpenBuyOrders", currentNode.get("OpenBuyOrders").asText())
                        .append("OpenSellOrders", currentNode.get("OpenSellOrders").asText())
                        .append("PrevDay", currentNode.get("PrevDay").asDouble())
                        .append("Created", toCurrentTimeZone(currentNode.get("Created").asText()));
                listOfDocs.add(doc);
            }
        }
        return listOfDocs;
    }

    private String toCurrentTimeZone(String str) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Calendar calendar = Calendar.getInstance();
        Date date = dateFormat.parse(str);
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, +3);
        dateFormat.setCalendar(calendar);
        String finalDate = dateFormat.format(calendar.getTime());
        return finalDate.substring(0, Math.min(finalDate.length(), 16));
    }


}
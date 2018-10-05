package com.denis.cryptoproject.models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.Document;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Model {

    private String MarketName;
    private Double High;
    private Double Low;
    private String Volume;
    private Double Last;
    private String BaseVolume;
    private String TimeStamp;
    private Double Bid;
    private Double Ask;
    private String OpenBuyOrders;
    private String OpenSellOrders;
    private Double PrevDay;
    private String Created;

    public Model() {

    }

    public String getMarketName() {
        return MarketName;
    }

    public void setMarketName(String marketName) {
        MarketName = marketName;
    }

    public Double getHigh() {
        return High;
    }

    public void setHigh(Double high) {
        High = high;
    }

    public Double getLow() {
        return Low;
    }

    public void setLow(Double low) {
        Low = low;
    }

    public String getVolume() {
        return Volume;
    }

    public void setVolume(String volume) {
        Volume = volume;
    }

    public Double getLast() {
        return Last;
    }

    public void setLast(Double last) {
        Last = last;
    }

    public String getBaseVolume() {
        return BaseVolume;
    }

    public void setBaseVolume(String baseVolume) {
        BaseVolume = baseVolume;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }

    public Double getBid() {
        return Bid;
    }

    public void setBid(Double bid) {
        Bid = bid;
    }

    public Double getAsk() {
        return Ask;
    }

    public void setAsk(Double ask) {
        Ask = ask;
    }

    public String getOpenBuyOrders() {
        return OpenBuyOrders;
    }

    public void setOpenBuyOrders(String openBuyOrders) {
        OpenBuyOrders = openBuyOrders;
    }

    public String getOpenSellOrders() {
        return OpenSellOrders;
    }

    public void setOpenSellOrders(String openSellOrders) {
        OpenSellOrders = openSellOrders;
    }

    public Double getPrevDay() {
        return PrevDay;
    }

    public void setPrevDay(Double prevDay) {
        PrevDay = prevDay;
    }

    public String getCreated() {
        return Created;
    }

    public void setCreated(String created) {
        Created = created;
    }

    public void printAllData() {
        System.out.println(MarketName);
        System.out.println(High);
        System.out.println(Low);
        System.out.println(Volume);
        System.out.println(Last);
        System.out.println(BaseVolume);
        System.out.println(TimeStamp);
        System.out.println(Bid);
        System.out.println(Ask);
        System.out.println(OpenBuyOrders);
        System.out.println(OpenSellOrders);
        System.out.println(PrevDay);
        System.out.println(Created);
    }

    public Model parsingJson(String json) throws IOException, ParseException {
        Model model = new Model();
        JsonNode node = new ObjectMapper().readTree(json);
        model.setMarketName(node.get("MarketName").asText());
        model.setHigh(node.get("High").asDouble());
        model.setLow(node.get("Low").asDouble());
        model.setVolume(node.get("Volume").asText());
        model.setLast(node.get("Last").asDouble());
        model.setBaseVolume(node.get("BaseVolume").asText());
        model.setTimeStamp(node.get("TimeStamp").asText());
        model.setBid(node.get("Bid").asDouble());
        model.setAsk(node.get("Ask").asDouble());
        model.setOpenBuyOrders(node.get("OpenBuyOrders").asText());
        model.setOpenSellOrders(node.get("OpenSellOrders").asText());
        model.setPrevDay(node.get("PrevDay").asDouble());
        model.setCreated(node.get("Created").asText());

        return model;
    }

    public String getDateInCurrentTimeZone(String str) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Calendar calendar = Calendar.getInstance();
        Date date = dateFormat.parse(str);
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, 2);

        return calendar.toString().substring(16);
    }
    public String getYesterdayToString(){
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        dateFormat.setCalendar(yesterday);
        return dateFormat.format(yesterday.getTime());
    }
}
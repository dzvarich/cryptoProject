package com.denis.cryptoproject.framework;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;

public class WorkingWithFinalData {

    private GettingDataFromDB gettingDataFromDB = new GettingDataFromDB();

    public HashMap<String, Double> getDoubleFor(List<String> platformn, String day) throws IOException {
        Double value;
        HashMap <String, Double> newList = new HashMap<>();
        HashMap<String, String> dataForEachCurrency = gettingDataFromDB.getData(platformn, day);
        for(String currency: platformn){
            try {
                value = Double.parseDouble(gettingDataFromDB.getValueOfNode(dataForEachCurrency.get(currency)));
                newList.put(currency, value);
            } catch (NumberFormatException n) {
                System.out.println("Cannot convert empty string");
            }
        }
        return newList;
    }

    public Double percentChange (Double value1, Double value2){
        BigDecimal bd = new BigDecimal((value2 - value1)/value1*100);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
package com.denis.cryptoproject.framework;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;

public class WorkingWithFinalData {

    private GettingDataFromDB gettingDataFromDB = new GettingDataFromDB();

    public HashMap<String, Double> getDoubleFor (List<String> listOfCurrencies, String day) throws IOException {
        Double doubleValue;
        HashMap <String, Double> resultList = new HashMap<>();
        HashMap <String, String> dataForEachCurrency = gettingDataFromDB.getData(listOfCurrencies, day);
        for(String currency: listOfCurrencies){
            try {
                String stringValue = gettingDataFromDB.getValueOfNode(dataForEachCurrency.get(currency), "Ask");
                doubleValue = Double.parseDouble(stringValue);
                resultList.put(currency, doubleValue);
            } catch (NumberFormatException n) {
                System.out.println("Cannot convert empty string");
            }
        }
        return resultList;
    }

    public Double percentChange (Double value1, Double value2){
        BigDecimal bd = new BigDecimal((value2 - value1)/value1*100);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
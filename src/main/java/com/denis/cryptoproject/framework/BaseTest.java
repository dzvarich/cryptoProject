package com.denis.cryptoproject.framework;

import com.denis.cryptoproject.utils.MongoDbConnection;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.util.List;
import java.util.logging.LogManager;

import static com.denis.cryptoproject.framework.Settings.getBittrexCurrencies;
import static com.denis.cryptoproject.framework.Settings.getCurrencyFromArray;

public class BaseTest {

    public static MongoDbConnection mongoDbConnection;
    public static DocumentGenerator documentGenerator;
    public static GettingDataFromDB gettingDataFromDB;
    public static WorkingWithFinalData workingWithFinalData;

    public static Settings settings;
    public static String today;
    public static String yesterday;
    public static String dayToDelete;
    public static List<String> bittrex;

    @BeforeMethod()
    public void beforeMethod() throws IOException {
        LogManager.getLogManager().reset();
        mongoDbConnection = new MongoDbConnection();
        documentGenerator = new DocumentGenerator();
        gettingDataFromDB = new GettingDataFromDB();
        workingWithFinalData = new WorkingWithFinalData();
        settings = new Settings();
        today = gettingDataFromDB.getTodayToString(0,0);
        yesterday = gettingDataFromDB.getTodayToString(-1,0);
        dayToDelete = gettingDataFromDB.getTodayToString(-1,-5);
        bittrex = getCurrencyFromArray(getBittrexCurrencies());
    }
}

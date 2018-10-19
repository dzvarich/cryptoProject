import com.denis.cryptoproject.framework.DocumentGenerator;
import com.denis.cryptoproject.framework.GettingDataFromDB;
import com.denis.cryptoproject.framework.Settings;
import com.denis.cryptoproject.framework.WorkingWithFinalData;
import com.denis.cryptoproject.utils.MongoDbConnection;
import org.bson.Document;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.LogManager;

import static com.denis.cryptoproject.framework.Settings.getBittrexCurrencies;
import static com.denis.cryptoproject.framework.Settings.getCurrencyFromArray;

public class Tests {
    private DocumentGenerator documentGenerator;
    private MongoDbConnection mongoDbConnection;
    private GettingDataFromDB gettingDataFromDB;
    private WorkingWithFinalData workingWithFinalData;

    private Settings settings;
    private String today;
    private String yesterday;
    private String dayToDelte;
    private List<String> bittrex;

    @BeforeMethod()
    public void beforeMethod() throws IOException {
        LogManager.getLogManager().reset();
        documentGenerator = new DocumentGenerator();
        mongoDbConnection = new MongoDbConnection();
        gettingDataFromDB = new GettingDataFromDB();
        workingWithFinalData = new WorkingWithFinalData();
        settings = new Settings();
        today = gettingDataFromDB.getTodayToString(0, 0);
        yesterday = gettingDataFromDB.getTodayToString(-1, 0);
        dayToDelte = gettingDataFromDB.getTodayToString(-1, -5);
        bittrex = getCurrencyFromArray(getBittrexCurrencies());
    }

    @Test
    public void addNewRecordToDB() throws IOException, ParseException {
        String jsonResult = documentGenerator.getJsonFromAPI(settings.getBittrexUrl());
        List<Document> docs = documentGenerator.createListOfDocs(jsonResult);
        mongoDbConnection.insertNewItem(docs);
    }

    @Test
    public void dataTest() throws IOException {
        HashMap<String, Double> value1 = workingWithFinalData.getDoubleFor(bittrex, yesterday);
        HashMap<String, Double> value2 = workingWithFinalData.getDoubleFor(bittrex, today);
        for (String currency : bittrex
        ) {
            try {
                System.out.println(currency + " = " + workingWithFinalData.percentChange(value1.get(currency), value2.get(currency)));
            } catch (NullPointerException e) {
                System.out.println("No data to compare for " + currency);
            }

        }

    }

    @Test
    public void removeData() {
        mongoDbConnection.removeRecords(dayToDelte);
    }


    @AfterMethod()
    public void afterMethod() {
        mongoDbConnection.mongoClient.close();
    }
}
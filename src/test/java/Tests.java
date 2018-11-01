import com.denis.cryptoproject.framework.*;
import org.bson.Document;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

public class Tests extends BaseTest {


    @Test
    public void addNewRecordToDB () throws IOException, ParseException {
        String jsonResult = documentGenerator.getJsonFromAPI(settings.getBittrexUrl());
        List <Document> docs = documentGenerator.createListOfDocs(jsonResult);
        mongoDbConnection.insertNewItem(docs);
    }

    @Test
    public void dataTest() throws IOException {
        HashMap<String, Double> value1 = workingWithFinalData.getDoubleFor(bittrex, yesterday);
        HashMap<String, Double> value2 = workingWithFinalData.getDoubleFor(bittrex, today);
        for (String currency : bittrex
        ) {
            try {
                System.out.println("\n"+currency + " Yesterday" + " = " + String.format("%.8f", value1.get(currency)) + "         "+ currency + " Today" + " = " + String.format("%.8f", value2.get(currency)));
                System.out.println(currency + " = " + workingWithFinalData.percentChange(value1.get(currency), value2.get(currency)) + " %\n");
            } catch (NullPointerException e) {
                System.out.println("No data to compare for " + currency);
            }
        }
    }
    @Test
    public void removeData (){
        mongoDbConnection.removeRecords(dayToDelete);
    }

    @AfterMethod()
    public void afterMethod () {
        mongoDbConnection.mongoClient.close();
    }
}
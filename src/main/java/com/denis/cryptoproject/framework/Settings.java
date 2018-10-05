package com.denis.cryptoproject.framework;


import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


public class Settings {

    private static final String SELENIUM_BITTREXURL = "selenium.bittrexUrl";
    private static final String SELENIUM_BINANCEURL = "selenium.binanceUrl";
    private static final String SELENIUM_POLONIEXURL = "selenium.poloniexUrl";
    private static final String SELENIUM_BITTREX_CURRENCIES = "selenium.bittrexCurrencies";
    private static final String SELENIUM_BINANCE_CURRENCIES = "selenium.binanceCurrencies";
    private static final String SELENIUM_POLONIEX_CURRENCIES = "selenium.poloniexCurrencies";
    private static final String SELENIUM_PROPERTIES = "selenium.properties";

    private String bittrexUrl;
    private String binanceUrl;
    private String poloniexUrl;
    private static String bittrexCurrencies;
    private String binanceCurrencies;
    private String poloniexCurrencies;

    private Properties properties = new Properties();
    public Settings() throws IOException {
        loadSettings();
        bittrexUrl = getPropertyOrThrowException(SELENIUM_BITTREXURL);
        bittrexCurrencies = getPropertyOrThrowException(SELENIUM_BITTREX_CURRENCIES);
        binanceUrl = getPropertyOrThrowException(SELENIUM_BINANCEURL);
        binanceCurrencies = getPropertyOrThrowException(SELENIUM_BINANCE_CURRENCIES);
        poloniexUrl = getPropertyOrThrowException(SELENIUM_POLONIEXURL);
        poloniexCurrencies = getPropertyOrThrowException(SELENIUM_POLONIEX_CURRENCIES);
    }

    private void loadSettings() throws IOException {
        properties = loadPropertiesFile();
    }


    private Properties loadPropertiesFile() throws IOException {
        try {
            // get specified property file
            String filename = getPropertyOrNull(SELENIUM_PROPERTIES);
            // it is not defined, use default
            if (filename == null) {
                filename = SELENIUM_PROPERTIES;
            }
            // try to load from classpath
            InputStream stream = getClass().getClassLoader().getResourceAsStream(filename);
            // no file in classpath, look on disk
            if (stream == null) {
                stream = new FileInputStream(new File(filename));
            }
            Properties result = new Properties();
            result.load(stream);
            return result;
        } catch (UnknownPropertyException e) {
            throw new UnknownPropertyException("Property file is not found");
        }
    }

    public String getPropertyOrNull(String name) {
        return getProperty(name, false);
    }

    public String getPropertyOrThrowException(String name) throws IOException {
        return getProperty(name, true);
    }

    private String getProperty(String name, boolean forceExceptionIfNotDefined) {
        String result;
        if ((result = System.getProperty(name, null)) != null && result.length() > 0) {
            return result;
        } else if ((result = getPropertyFromPropertiesFile(name)) != null && result.length() > 0) {
            return result;
        } else if (forceExceptionIfNotDefined) {
            throw new UnknownPropertyException("Unknown property: [" + name + "]");
        }
        return result;
    }

    private String getPropertyFromPropertiesFile(String name) {
        Object result = properties.get(name);
        if (result == null) {
            return null;
        } else {
            return result.toString();
        }
    }

    //getting path to the Icon
    public String getBittrexUrl() {
        if (bittrexUrl.length() > 0) {
            return bittrexUrl;
        } else {
            throw new UnknownPropertyException("Path for Icon is not defined");
        }
    }

    public static String getBittrexCurrencies() {
        if (bittrexCurrencies.length() > 0) {
            return bittrexCurrencies;
        } else {
            throw new UnknownPropertyException("Path for Icon is not defined");
        }
    }

    public String getBinanceUrl() {
        if (binanceUrl.length() > 0) {
            return binanceUrl;
        } else {
            throw new UnknownPropertyException("Path for Icon is not defined");
        }
    }

    public String getBinanceCurrencies() {
        if (binanceCurrencies.length() > 0) {
            return binanceCurrencies;
        } else {
            throw new UnknownPropertyException("Path for Icon is not defined");
        }
    }

    public String getPoloniexUrl() {
        if (poloniexUrl.length() > 0) {
            return poloniexUrl;
        } else {
            throw new UnknownPropertyException("Path for Icon is not defined");
        }
    }

    public String getPoloniexCurrencies() {
        if (poloniexCurrencies.length() > 0) {
            return poloniexCurrencies;
        } else {
            throw new UnknownPropertyException("Path for Icon is not defined");
        }
    }

    public static List<String> getCurrencyFromArray (String str){
        return Arrays.asList(str.trim().split("\\s*,\\s*"));
    }
}
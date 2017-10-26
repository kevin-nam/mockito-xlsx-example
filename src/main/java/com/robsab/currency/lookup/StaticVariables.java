package com.robsab.currency.lookup;

/**
 * Static variables utilized by CurrencyRateLookup class
 */
public final class StaticVariables {

  // Selenium Chrome Driver Variables
  public static final String PATH_TO_CHROME_DRIVER_MAC = "src/main/resources/drivers/chromedriver-mac";
  public static final String PATH_TO_CHROME_DRIVER_WINDOWS = "src/main/resources/drivers/chromedriver-windows.exe";
  public static final int TIMEOUT = 5;

  // Bank of Canada Currency Converter URL
  public static final String BANK_OF_CANADA_CURRENCY_LOOK_UP_URL = "http://www.bankofcanada.ca/rates/exchange/currency-converter/";

  // Selectors for Bank of Canada Currency Converter Pages
  public static final String AMOUNT_INPUT_ID = "convert";
  public static final String RANGE_INPUT_ID = "rangeSelector";
  public static final String ONE_YEAR_VALUE_SELECT = "1.y";
  public static final String SUBMIT_BTN_ID = "f6";
  public static final String RATE_TABLE_CLASS_SELECTORS = ".table-hover.summary-rates.rates.series tbody";

}

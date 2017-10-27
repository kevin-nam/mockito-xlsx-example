package com.robsab.currency;

import com.robsab.currency.lookup.CurrencyRate;
import com.robsab.currency.lookup.CurrencyRateLookup;
import com.robsab.currency.xlsx.XlsxWriter;

/**
 * Application class
 * Run the main method to demo the application
 */
public class CurrencyRateXlsxApplication {

  private CurrencyRateLookup currencyRateLookup;
  private XlsxWriter xlsxWriter;

  /**
   * Main method to demo the applicatiom
   */
  public static void main(String[] args) {
    CurrencyRateXlsxApplication application = new CurrencyRateXlsxApplication();
    application.convertValueAndWriteToXlsx(100.00);
  }

  /**
   * Constructors
   */
  public CurrencyRateXlsxApplication() {
    this.currencyRateLookup = new CurrencyRateLookup();
    this.xlsxWriter = new XlsxWriter();
  }

  public CurrencyRateXlsxApplication(CurrencyRateLookup currencyRateLookup,
      XlsxWriter xlsxWriter) {
    this.currencyRateLookup = currencyRateLookup;
    this.xlsxWriter = xlsxWriter;
  }

  public CurrencyRateXlsxApplication(CurrencyRateLookup currencyRateLookup) {
    this.currencyRateLookup = currencyRateLookup;
    this.xlsxWriter = new XlsxWriter();
  }

  public CurrencyRateXlsxApplication(XlsxWriter xlsxWriter) {
    this.currencyRateLookup = new CurrencyRateLookup();
    this.xlsxWriter = xlsxWriter;
  }

  /**
   * Looks up the highest currency rate of the year and
   * writes (valueToBeConverted, rate, convertedValue) to an xlsx file
   * (by default: converted-currency.xlsx)
   */
  public CurrencyRate convertValueAndWriteToXlsx(double valueToBeConverted) {
    System.out.println("Starting application...");

    // Get highest currency rate
    CurrencyRate currencyRate = currencyRateLookup.lookupHighestCurrencyRate();

    // Write to xlsx file
    xlsxWriter.writeConvertedCurrencyXlsxFileWithValue(valueToBeConverted, currencyRate);

    return currencyRate;
  }

}

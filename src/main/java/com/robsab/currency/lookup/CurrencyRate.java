package com.robsab.currency.lookup;

/**
 * Class to encapsulate all information about a currency rate
 */
public class CurrencyRate {

  private double rate;
  private String date;

  public CurrencyRate(double rate, String date) {
    this.rate = rate;
    this.date = date;
  }

  public double getRate() {
    return rate;
  }

  public void setRate(double rate) {
    this.rate = rate;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }
}

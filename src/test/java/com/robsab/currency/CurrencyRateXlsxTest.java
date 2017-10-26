package com.robsab.currency;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;

import com.robsab.currency.lookup.CurrencyRate;
import com.robsab.currency.lookup.CurrencyRateLookup;
import com.robsab.currency.xlsx.XlsxWriter;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Random;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class CurrencyRateXlsxTest {

  private double randomValue;
  private double randomRate;

  private final String DATE = "2017-09-11";

  @Before
  public void setup() {
    randomValue = formatValue((new Random()).nextDouble(), 100);
    randomRate = (new Random()).nextDouble();
  }

  @Test
  public void testApplication() {
    CurrencyRateXlsxApplication application = new CurrencyRateXlsxApplication();
    CurrencyRate currencyRate = application.convertValueAndWriteToXlsx(randomValue);

    Double expectedValue = TestHelper.getConvertedValueFromBankOfCanada(randomValue, currencyRate.getDate());
    Double xlsxValue = TestHelper.getConvertedValueFromXlsxFile();

    Assert.assertEquals("Converted value in xlsx should be equal to value found on Bank of Canada", expectedValue, xlsxValue);
  }

  @Test
  public void testApplicationWithoutCurrencyRateLookup() {
    // Mock currency rate lookup
    CurrencyRateLookup currencyRateLookup = Mockito.mock(CurrencyRateLookup.class);
    Mockito.when(currencyRateLookup.lookupHighestCurrencyRate()).thenReturn(
        createRandomCurrencyRate());

    // Instantiate and run the application
    CurrencyRateXlsxApplication application = new CurrencyRateXlsxApplication(currencyRateLookup);
    application.convertValueAndWriteToXlsx(randomValue);

    // Verify that expected value is equal to value in xlsx
    Double expectedValue = formatValue(randomValue, randomRate);
    Double xlsxValue = TestHelper.getConvertedValueFromXlsxFile();

    Assert.assertEquals("Converted value in xlsx should be equal to " + randomValue + " * " + randomRate, expectedValue, xlsxValue);
  }

  @Test
  public void testApplicationWithoutXlsxWriter() {
    // Mock xlsx writer
    XlsxWriter xlsxWriter = Mockito.mock(XlsxWriter.class);
    Mockito.when(xlsxWriter.writeConvertedCurrencyXlsxFileWithValue(anyDouble(), any(CurrencyRate.class))).thenReturn(true);

    // Instantiate and run the application
    CurrencyRateXlsxApplication application = new CurrencyRateXlsxApplication(xlsxWriter);
    CurrencyRate currencyRate = application.convertValueAndWriteToXlsx(randomValue);

    // Verify that calculate value and expected value is the same from Bank of Canada
    Double calculatedValue = formatValue(currencyRate.getRate(), randomValue);
    Double expectedValue = TestHelper.getConvertedValueFromBankOfCanada(randomValue, currencyRate.getDate());

    Assert.assertEquals("Converted calculated value should be equal to value found on Bank of Canada", expectedValue, calculatedValue);
  }

  private double formatValue(double value, double rate) {
    DecimalFormat df = new DecimalFormat("#.##");
    df.setRoundingMode(RoundingMode.CEILING);
    return Double.valueOf(df.format(value * rate));
  }

  private CurrencyRate createRandomCurrencyRate() {
    return new CurrencyRate(randomRate, DATE);
  }
}

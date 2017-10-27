package com.robsab.currency.lookup;

import static com.robsab.currency.lookup.StaticVariables.AMOUNT_INPUT_ID;
import static com.robsab.currency.lookup.StaticVariables.BANK_OF_CANADA_CURRENCY_LOOK_UP_URL;
import static com.robsab.currency.lookup.StaticVariables.ONE_YEAR_VALUE_SELECT;
import static com.robsab.currency.lookup.StaticVariables.PATH_TO_CHROME_DRIVER_MAC;
import static com.robsab.currency.lookup.StaticVariables.PATH_TO_CHROME_DRIVER_WINDOWS;
import static com.robsab.currency.lookup.StaticVariables.RANGE_INPUT_ID;
import static com.robsab.currency.lookup.StaticVariables.RATE_TABLE_CLASS_SELECTORS;
import static com.robsab.currency.lookup.StaticVariables.SUBMIT_BTN_ID;
import static com.robsab.currency.lookup.StaticVariables.TIMEOUT;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Class that utilizes Selenium to look up the Bank of Canada Currency Converter site
 * to acquire the highest currency rate of the year
 */
public class CurrencyRateLookup {

  private WebDriver driver;

  public CurrencyRateLookup() {
    setupChromeDriver();
  }

  // Setup Chrome Driver depending on OS
  private void setupChromeDriver() {
    String os = System.getProperty("os.name").toLowerCase();
    if (os.contains("mac")) {
      System.setProperty("webdriver.chrome.driver", PATH_TO_CHROME_DRIVER_MAC);
    } else {
      System.setProperty("webdriver.chrome.driver", PATH_TO_CHROME_DRIVER_WINDOWS);
    }
    driver = new ChromeDriver();
  }

  public CurrencyRate lookupHighestCurrencyRate() {
    System.out.println("Looking up " + BANK_OF_CANADA_CURRENCY_LOOK_UP_URL + " for highest currency rate of this year...");
    driver.get(BANK_OF_CANADA_CURRENCY_LOOK_UP_URL);

    // Input value
    WebElement amountInput = (new WebDriverWait(driver, TIMEOUT))
        .until(ExpectedConditions.presenceOfElementLocated(By.id(AMOUNT_INPUT_ID)));

    // Input range
    Select rangeInput = new Select((new WebDriverWait(driver, TIMEOUT))
        .until(ExpectedConditions.presenceOfElementLocated(By.id(RANGE_INPUT_ID))));
    rangeInput.selectByValue(ONE_YEAR_VALUE_SELECT);

    // Submit button
    WebElement submitButton = (new WebDriverWait(driver, TIMEOUT))
        .until(ExpectedConditions.presenceOfElementLocated(By.id(SUBMIT_BTN_ID)));
    submitButton.click();

    // Find rate table
    WebElement rateTable = (new WebDriverWait(driver, TIMEOUT))
        .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(
            RATE_TABLE_CLASS_SELECTORS)));

    // Get highest rate information
    String[] rates = rateTable.getText().split("\n")[2].split(" ");

    System.out.println("Got currency rate: " + rates[2] + " for date " + rates[1]);

    // Close chrome driver
    driver.quit();

    return new CurrencyRate(Double.valueOf(rates[2]), rates[1]);
  }

}

package com.robsab.currency;

import static com.robsab.currency.lookup.StaticVariables.AMOUNT_INPUT_ID;
import static com.robsab.currency.lookup.StaticVariables.BANK_OF_CANADA_CURRENCY_LOOK_UP_URL;
import static com.robsab.currency.lookup.StaticVariables.ONE_YEAR_VALUE_SELECT;
import static com.robsab.currency.lookup.StaticVariables.PATH_TO_CHROME_DRIVER_MAC;
import static com.robsab.currency.lookup.StaticVariables.PATH_TO_CHROME_DRIVER_WINDOWS;
import static com.robsab.currency.lookup.StaticVariables.RANGE_INPUT_ID;
import static com.robsab.currency.lookup.StaticVariables.SUBMIT_BTN_ID;
import static com.robsab.currency.lookup.StaticVariables.TIMEOUT;
import static com.robsab.currency.xlsx.XlsxWriter.FILE_NAME;

import com.google.common.collect.Lists;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Test helper class to facilitate testing. Has two functions:
 * 1) Gets converted value as found on the Bank of Canada currency converter
 * 2) Reads converted value from xlsx file
 */
public class TestHelper {

  private static WebDriver driver;

  private static final String CONVERTED_TABLE_SELECTOR = ".table.table-bordered.table-striped.table-hover.results-rates.rates.sortable.series tbody";


  // Setup Chrome Driver depending on OS
  private static void setupChromeDriver() {
    String os = System.getProperty("os.name").toLowerCase();
    if (os.contains("mac")) {
      System.setProperty("webdriver.chrome.driver", PATH_TO_CHROME_DRIVER_MAC);
    } else {
      System.setProperty("webdriver.chrome.driver", PATH_TO_CHROME_DRIVER_WINDOWS);
    }
    driver = new ChromeDriver();
  }

  public static double getConvertedValueFromBankOfCanada(double value, String date) {
    setupChromeDriver();

    driver.get(BANK_OF_CANADA_CURRENCY_LOOK_UP_URL);

    // Input value
    WebElement amountInput = (new WebDriverWait(driver, TIMEOUT))
        .until(ExpectedConditions.presenceOfElementLocated(By.id(AMOUNT_INPUT_ID)));
    amountInput.sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE);
    amountInput.sendKeys(String.valueOf(value));

    // Input range
    Select rangeInput = new Select((new WebDriverWait(driver, TIMEOUT))
        .until(ExpectedConditions.presenceOfElementLocated(By.id(RANGE_INPUT_ID))));
    rangeInput.selectByValue(ONE_YEAR_VALUE_SELECT);

    // Submit button
    WebElement submitButton = (new WebDriverWait(driver, TIMEOUT))
        .until(ExpectedConditions.presenceOfElementLocated(By.id(SUBMIT_BTN_ID)));
    submitButton.click();

    // Find converted value table
    WebElement convertedTableSelector = (new WebDriverWait(driver, TIMEOUT))
        .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(
            CONVERTED_TABLE_SELECTOR)));

    // Find converted value at date
    List<String> listOfConvertedValuesByDate = Lists.newArrayList(convertedTableSelector.getText().split("\n"));
    Optional<String> result = listOfConvertedValuesByDate.stream().filter(s -> s.indexOf(date) == 0).findAny();

    driver.quit();

    return Double.valueOf(result.get().split(" ")[1]);
  }

  public static double getConvertedValueFromXlsxFile() {
    try {

      // Get xlsx file
      FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
      Workbook workbook = new XSSFWorkbook(excelFile);

      // Go to second tab
      Sheet convertedValueSheet = workbook.getSheetAt(1);

      // Setup iterator
      Iterator<Row> iterator = convertedValueSheet.iterator();

      // Find value at C2
      Row row1 = iterator.next();
      Row row2 = iterator.next();

      return Double.valueOf(row2.getCell(2).getStringCellValue());

    } catch (IOException e) {
      e.printStackTrace();
    }

    return 0;
  }


}

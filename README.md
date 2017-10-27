# mockito-xlsx-example
By: Kevin Nam (kevin.nam@mail.mcgill.ca)
For: Robert Sabourin

## Description
Example project for getting a currency rate from Bank of Canada and writing (value, rate, converted value) into an xlsx file. Tests feature Mockito in the event of no web access or xlsx writing/reading capabilities.

## Requirements
1. JDK v1.8 [Link](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
2. Maven v3.3+ [Link](https://maven.apache.org/download.cgi)
3. IntelliJ 2017.1.3 Community Edition, with plugins:
4. Google Chrome Driver

## Dependencies Used

Using Maven, all dependencies can be easily acquired and used. Refer to */pom.xml* for more details.

```
<dependencies>
    <dependency>
      <groupId>org.seleniumhq.selenium</groupId>
      <artifactId>selenium-java</artifactId>
      <version>3.4.0</version>
    </dependency>
    <dependency>
      <groupId>org.apache.poi</groupId>
      <artifactId>poi-ooxml</artifactId>
      <version>3.17</version>
    </dependency>
    <dependency>
      <groupId>org.mockito</groupId>
      <artifactId>mockito-all</artifactId>
      <version>1.10.19</version>
      <scope>test</scope>
    </dependency>
</dependencies>
```

## Classes Explained

1. com.robsab.currency.lookup.**CurrencyRateLookup**
  * Class that utilizes Selenium to look up the Bank of Canada Currency Converter site to acquire the highest currency rate of the year
2. com.robsab.currency.lookup.**StaticVariables**
  * Contains static variables utilized by CurrencyRateLookup class
3. com.robsab.currency.lookup.**CurrencyRate**
  * Class to encapsulate all information about a currency rate
4. com.robsab.currency.xlsx.**XlsxWriter**
  * Class used to write (value, rate, converted value) to an xlsx file
5. com.robsab.currency.**CurrencyRateXlsxApplication**
  * Contains the main method to demo the application
  * Main class to test]

## Tests Explained

Three tests (found under src/test/robsab/currency) have been implemented to express the use of Mockito:

1. testApplication()
  * Runs the application as normal and verifies the converted random value in the xlsx file and the converted value from the Bank of Canada.
2. testApplicationWithoutCurrencyRateLookup()
  * Runs the application while mocking the CurrencyRateLookup class and verifies that the converted random value in xlsx file is equal to a calculated expected value.
3. testApplicationWithoutXlsxWriter()
  * Runs the application while mocking the Xlsx class and verifies that the converted calculated random value is equal to the converted value from the Bank of Canada.


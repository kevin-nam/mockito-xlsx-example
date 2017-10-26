package com.robsab.currency.xlsx;

import com.robsab.currency.lookup.CurrencyRate;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Class used to write (value, rate, converted value) to an xlsx file
 */
public class XlsxWriter {

  // Desired file name
  public static final String FILE_NAME = "converted-currency.xlsx";

  public boolean writeConvertedCurrencyXlsxFileWithValue(double value, CurrencyRate currencyRate) {
    XSSFWorkbook workbook = new XSSFWorkbook();

    // Create unused first tab/sheet
    workbook.createSheet("Not Converted Currency");

    // Create second useful tab/sheet
    XSSFSheet secondSheet = workbook.createSheet("Converted Currency");

    // Get converted value based on currency rate and format appropriately
    DecimalFormat df = new DecimalFormat("#.##");
    df.setRoundingMode(RoundingMode.CEILING);
    double convertedValue = Double.valueOf(df.format(value * currencyRate.getRate()));

    // Form table to write
    Object[][] table = {
        {"Value", "Rate", "Converted Value"},
        {String.format("%.2f", value), currencyRate.getRate(), String.format("%.2f", convertedValue)},
    };

    System.out.println("Creating xlsx file...");

    // Iterate through table by row
    int rowNum = 0;
    for (Object[] elements : table) {
      Row row = secondSheet.createRow(rowNum++);

      // Iterate through table by column
      int colNum = 0;
      for (Object field : elements) {
        Cell cell = row.createCell(colNum++);
        if (field instanceof String) {
          cell.setCellValue((String) field);
        } else if (field instanceof Double) {
          cell.setCellValue((Double) field);
        }
      }
    }

    // Write to xlsx
    try {
      FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
      workbook.write(outputStream);
      workbook.close();
      System.out.println("Done");
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

}

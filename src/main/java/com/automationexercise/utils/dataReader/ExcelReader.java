package com.automationexercise.utils.dataReader;

import com.automationexercise.utils.logs.LogsManager;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;

public class ExcelReader {
    private static final String testDataPath = "src/test/resources/testdata/";

    //bad practice
    public static String getExcelData(String excelFilename, String sheetName, int rowNum, int colNum) {
        XSSFWorkbook workBook;
        XSSFSheet sheet;
        String cellData;
        try {
            workBook = new XSSFWorkbook( testDataPath + excelFilename);
            sheet = workBook.getSheet(sheetName);
            cellData = sheet.getRow(rowNum).getCell(colNum).getStringCellValue();
            return cellData;

        } catch (IOException e) {
            LogsManager.error("Error reading Excel file: ", excelFilename, " - ", e.getMessage());
            return "";
        }
    }
}

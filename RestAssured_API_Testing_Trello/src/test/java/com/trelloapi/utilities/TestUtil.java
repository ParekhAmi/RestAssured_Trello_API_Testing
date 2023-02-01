package com.trelloapi.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestUtil{
	
	public static String TESTDATA_SHEET_PATH = "D:\\QA\\Testing\\Trello_Testdata.xlsx";
	public static XSSFWorkbook book;
	public static XSSFSheet sheet;

	public static Object[][] getTestData(String sheetName) {

		FileInputStream file = null;

		try {
			file = new FileInputStream(TESTDATA_SHEET_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			book = new XSSFWorkbook(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		sheet = book.getSheet(sheetName);

		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		
		System.out.println("Row count"+sheet.getLastRowNum());
		System.out.println("Column count"+sheet.getRow(0).getLastCellNum());

		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {

				data[i][j] = sheet.getRow(i+1).getCell(j).toString();
				System.out.println(data[i][j]);
			}
		}
		return data;
	}
}

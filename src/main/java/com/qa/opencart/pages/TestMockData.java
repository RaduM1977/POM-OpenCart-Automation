package com.qa.opencart.pages;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;



public class TestMockData {
	
	private static final String TEST_DATA_SHEET_PATH = "./src/test/resources/testData/OpenCartTestData.xlsx";
	
	private static Workbook workbook;
	private static Sheet sheet;
	static Object data[][];
	

	public static void main(String[] args) {
		
		System.out.println(System.currentTimeMillis());
		
		Calendar date =  Calendar.getInstance();
		Date time  = date.getTime();
		System.out.println(time.toString());
		
		System.out.println("=============");
		String sheetName = "product";
		
		System.out.println("reading data from sheet: " + sheetName);
		
		//Object data[][] = null;
		
		try {
			FileInputStream ip = new FileInputStream(TEST_DATA_SHEET_PATH);
			
			workbook = WorkbookFactory.create(ip); 
			sheet = workbook.getSheet(sheetName);
			
			//the size of the Object array will be the total numbers of row and the numbers of columns(the first row will be there all the time and get the last cell number)
			data = new Object [sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
			System.out.println(Arrays.deepToString(data));
			System.out.println(sheet.getLastRowNum());
			
			for(int i=0; i< sheet.getLastRowNum();i++) {
				for(int j =0; j< sheet.getRow(0).getLastCellNum(); j++) {
					data[i][j] = sheet.getRow(i+1).getCell(j).toString();
					System.out.println(data[i][j]);
				}
				System.out.println("-------");
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			
			e.printStackTrace();
		} catch (InvalidFormatException e) {
	
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		System.out.println( Arrays.deepToString(data));
	}

}

package com.vTiger.Library;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


public class Generic {
	public static int getXLRowCount(String xlPath,String sheetName)
	{
		int rowCount;
		try
		{
			FileInputStream fis = new FileInputStream(xlPath);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet s = wb.getSheet(sheetName);
			rowCount = s.getLastRowNum();
		}
		catch(Exception e)
		{
			rowCount = -1;
		}
		return rowCount;
	 }

	public static String getXLCellValue(String xlPath, String sheetName, int rowNum, int cellNum)
	{
		String cellValue;
		try
		{
			FileInputStream fis= new FileInputStream(xlPath);
			Workbook wb =WorkbookFactory.create(fis);
			Sheet s = wb.getSheet(sheetName);
			cellValue = s.getRow(rowNum).getCell(cellNum).getStringCellValue();
		}
		catch(Exception e)
		{
			cellValue="";
		}
		return cellValue;
	}	
	
	public static void explicitWait(int sec)
	{
		try
		{
			Thread.sleep(sec*1000);
		}
		catch(InterruptedException e)
		{

		}

	}
}
package com.vTiger.Library;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;


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

class MyDateTime{
	String dateToSelect, meetingDate=null, meetingTime=null, meridian=null, temp=null;
	int curDay, monthNow;
	Calendar cal;
	
	//Call Constructor
	MyDateTime(){
		//Create an instance of Calendar class
		 cal = Calendar.getInstance();
		//Find the current month
		  monthNow = cal.get(Calendar.MONTH);
		//Find the current day
		  curDay=cal.get(Calendar.DAY_OF_WEEK);
	}
	
	String checkDay(){
		//If the current day is  Sat/Sun then add 2 or 1 day(s) to schedule the meeting on a weekday
		if (curDay == 1) {  
			cal.add(Calendar.DATE, 1);
		}	
		else if (curDay == 7){  //DAY_OF_WEEK returns 7 if it is Saturday
			cal.add(Calendar.DATE, 2);

		}
		
		//Add one hour to current as meeting start time
		cal.add(Calendar.HOUR_OF_DAY,1);
		if((cal.get(Calendar.HOUR_OF_DAY) == 23) && curDay==6){
					cal.add(Calendar.DATE, 3);
		}

		Date date = cal.getTime();             
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
		return dateToSelect = dateFormat.format(date);   
	}

	String meetDate(){
		return meetingDate=dateToSelect.substring(8,10);
	}

	String  meetTime(){
		return meetingTime=dateToSelect.substring(11,13);	
	}

	String  meetMeridian() {
		return meridian=dateToSelect.substring(20);
	}

	//Get the current month to find out if the month has changed after adding 2/1 day(s) 
	int retMonth(){
		int monthAfter = cal.get(Calendar.MONTH);
		return monthAfter;
	}
	
} //end of MyDateTime class


class MyActionClass{
	void myActionClass(WebDriver driver, WebElement arg1)
	{
		//Create instance of Actions class and pass the WebDriver as argument
		Actions actions = new Actions(driver);
		actions.moveToElement(arg1).build().perform();
		
	}
	
	void myContextClick(WebDriver driver, WebElement arg1){
		Actions actions = new Actions(driver);
		actions.contextClick(arg1).perform();
		actions.sendKeys("W").perform();
	}
}//end of MyActionClass class


class MySelectClass{
	Select sSelect;

	void prepareSelect(WebDriver driver, String attribute, String value)
	{
		switch(attribute){
		case "name":
			sSelect = new Select(driver.findElement(By.name(value)));
		break;
			
		case "id":
			sSelect = new Select(driver.findElement(By.id(value)));
		break;
		
		case "css":
			sSelect = new Select(driver.findElement(By.cssSelector(value)));
		break;	
		}
	}
	
	void mySelectClassByValue(String choice)
	{
		sSelect.selectByValue(choice);	
	}
	
	void mySelectClassByIndex(int choice)
	{
		sSelect.selectByIndex(choice);	
	}
	void mySelectClassByVisibleText(String choice)
	{
		sSelect.selectByVisibleText(choice);	
	}
	
}//end of MySelectClass class 


class MyChildBrowserclass{
	 String[] myGetWindowHandles(WebDriver driver){	
		//Handle the Child Browser
		Set <String> setWH	= driver.getWindowHandles();
		Iterator <String> itWH = setWH.iterator();
		int setSize = setWH.size();
		int n=0;
		String[] arrayWH;
		arrayWH = new String[setSize];

		while (itWH.hasNext())
		{
			arrayWH[n] = itWH.next();
			n++;
		}
		return arrayWH;	
	}	
	
	void switchToChild(WebDriver driver, String childWH){
		driver.switchTo().window(childWH);
	}
	
	void switchToParent(WebDriver driver, String parentWH){	
		driver.switchTo().window(parentWH);
	}	

	void closeDriver(WebDriver driver){
		driver.close();
	}

}

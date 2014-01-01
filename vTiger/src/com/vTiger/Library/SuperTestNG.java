package com.vTiger.Library;


import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class SuperTestNG {
	public static WebDriver driver;
	public static WebDriverWait wait;
	
	@BeforeMethod
	public void preCondition()
	{
		String xlPath ="./testdatas/config.xlsx";
		String sheetName ="sheet1";		
		String browserType=Generic.getXLCellValue(xlPath, sheetName, 0, 1);
	
		if(browserType.equals("GC"))
		{
			System.setProperty("webdriver.chrome.driver", "./exefiles/chromedriver.exe");
			driver = new ChromeDriver();
			Reporter.log("Launching Chrome Browser",true);
		}
		
		else if(browserType.equals("IE")) {
			System.setProperty("webdriver.IEDriverServer", "./exefiles/IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			Reporter.log("Launching IE Browser",true);
		}
			
		else
		{
			driver = new FirefoxDriver();
		}
		
		String URL = Generic.getXLCellValue(xlPath, sheetName, 1, 1);
		driver.get(URL);
		Reporter.log("Navigating to URL:" +URL,true);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		Reporter.log("TimeOut is set to 10 Sec and Browser is maximized",true);
	}//End of Before method
		
//	@AfterMethod
	public void postCondition()
	{
		driver.quit();
		Reporter.log("Closing the Browser", true);
	}//End of AfterMethod

}//End of SuperTestNG class

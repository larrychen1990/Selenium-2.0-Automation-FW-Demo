package com.vTiger.Library;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;

public class ProjectSpecific extends SuperTestNG
	{
		//1.Login
		public static void login(String un, String pw)
		{
			//driver.findElement(By.name("user_name")).clear();
			//driver.findElement(By.name("user_password")).clear();
			driver.findElement(By.name("user_name")).sendKeys(un);
			driver.findElement(By.name("user_password")).sendKeys(pw);
			driver.findElement(By.id("submitButton")).click();
		}
		
		//2.Logout
		public static void logout()
		{
			//WebElement signout =driver.findElement(By.xpath("//td[3]/table/tbody/tr/td[2]"));
			WebElement signout =driver.findElement(By.cssSelector("img[src$='user.PNG']"));
			Actions actions = new Actions(driver);
			actions.moveToElement(signout).build().perform();
			driver.findElement(By.linkText("Sign Out")).click();
		}
			
		//3.gotoModule() -Selects the required module
		public static void gotoModule(String moduleName)
		//public static Boolean gotoModule(String moduleName)
		{
			driver.findElement(By.linkText(moduleName)).click();
			Generic.explicitWait(3);

			/*try {
				driver.findElement(By.linkText(moduleName)).click();
				Generic.explicitWait(3);
				return true;
			} catch (NoSuchElementException e) {
				Reporter.log("Element Not Found",true);
				ProjectSpecific.logout();
				return false;
			}*/
		}//End of Method

		//4.create Lead 
		public static void createLead(String title,String firstName ,String lastName ,String company,String industry ,String assignedTo,String team )
		{
			driver.findElement(By.xpath("//img[@alt='Create Lead...']")).click();
			Select tSelect,cSelect; 
			tSelect = new Select(driver.findElement(By.name("salutationtype")));
			tSelect.selectByValue(title);
			driver.findElement(By.name("firstname")).sendKeys(firstName);
			driver.findElement(By.name("lastname")).sendKeys(lastName);
			driver.findElement(By.name("company")).sendKeys(company);
			cSelect = new Select(driver.findElement(By.name("industry")));
			cSelect.selectByValue(industry);
		
			if (assignedTo.equals("User")){
				driver.findElement(By.xpath("//input[@value='U']")).click();
				tSelect = new Select(driver.findElement(By.name("assigned_user_id")));
				tSelect.selectByIndex(0);
			} 
			else if (assignedTo.equals("Group")){
				driver.findElement(By.xpath("//input[@value='T']")).click();
				tSelect = new Select(driver.findElement(By.name("assigned_group_id")));
				tSelect.selectByIndex(1);
			}
	
			driver.findElement(By.xpath("//input[@type='submit']")).click();			
		}
	
		//5.Add Description to existing Leads
		public static void addLeadDesc(String leadName)
		{
			//Get the WebTable handle
			WebElement element=driver.findElement(By.xpath("//table[@class='lvt small']"));
					
			//Retrieve all the rows
			List<WebElement> rowCollection=element.findElements(By.xpath("//*[@class='lvt small']/tbody/tr"));
			
			//System.out.println("Number of rows in this table: "+rowCollection.size());
			int rowSize=rowCollection.size();

			//Omit the first 2 rows and start to fetch from the Lead row 
			for (int j = 2; j < rowSize; j++) {
				//Retrieve all the columns
				List<WebElement> colCollection=rowCollection.get(j).findElements(By.xpath("td"));
				
				//Fetch the Last Name of the Lead from the second column and Compare the leadName's 
				String str=colCollection.get(2).getText();
				
				if (str.equals(leadName)) {
					//Click on the Lead to update the info
					driver.findElement(By.linkText(leadName)).click();
					Generic.explicitWait(3);
				
					//Update the info on the Description Tab
					driver.findElement(By.xpath("//td[@id='mouseArea_Description']")).click();
					driver.findElement(By.linkText("Edit")).click();
					driver.findElement(By.id("txtbox_Description")).sendKeys("Added the Desc - " 
									+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\n");
		  			
		  			Generic.explicitWait(1);
		  			driver.findElement(By.xpath("//input[@value='Save' and @name='button_Description']")).click();
		  			break;
				} //End if  
			} //End of 'for' loop
		} //End Method

		//6.ADD products randomly to 2 Leads
		public static void addLeadProducts()
		{
			//Get the WebTable handle
			WebElement element=driver.findElement(By.xpath("//table[@class='lvt small']"));

			//Retrieve all the rows
			List<WebElement> rowCollection=element.findElements(By.xpath("//*[@class='lvt small']/tbody/tr"));
			
			//System.out.println("Number of rows in this table: "+rowCollection.size());
			int rowSize=rowCollection.size();
			
			//Omit the first 2 rows and randomly select 2 Leads and the add the Product 
			for (int j = rowSize-2; j < rowSize; j++) {

				//Get the WebTable handle again as the page refreshes for every iteration
				WebElement prodElement=driver.findElement(By.xpath("//table[@class='lvt small']"));
				List<WebElement> prodRowCollection=prodElement.findElements(By.xpath("//*[@class='lvt small']/tbody/tr"));
				
				//Retrieve all the columns
				List<WebElement> colCollection=prodRowCollection.get(j).findElements(By.xpath("td"));
				
				//Fetch the Last Name of the LeadName from the second column
				String leadName=colCollection.get(2).getText();
				driver.findElement(By.linkText(leadName)).click();
				Generic.explicitWait(3);

				//Update the info on the Description Tab
				driver.findElement(By.linkText("More Information")).click();
				
				driver.findElement(By.xpath("//*[@id='show_Leads_Products']")).click();
				driver.findElement(By.xpath("//input[@value='Select Products']")).click();
				Generic.explicitWait(3);
				
				//Handle the Child Browser
				Iterator <String> allWH	= driver.getWindowHandles().iterator();
				String parentWH = allWH.next();
				String childWH =  allWH.next();
				driver.switchTo().window(childWH);
				
				//driver.findElement(By.xpath("//tr[2]/td[1]/input[@name='selected_id']")).click();
				driver.findElement(By.xpath("//tr[2]/td[2]/a")).click();
				Generic.explicitWait(3);
				
				//Transfer the control back to Parent Browser
				driver.switchTo().window(parentWH);
			
				ProjectSpecific.gotoModule("Leads");
			}//End for
		}//End Method

		//7. Click on the search Letter to search for Leads whose Last Name matches the letter clicked
		public static String[] searchLead(String searchChar){
			//Click on the required search Letter
			switch(searchChar){
				case "A":
				case "a":	
					driver.findElement(By.id("alpha_1")).click();
					break;
		
				case "O":
				case "o":	
					driver.findElement(By.id("alpha_15")).click();
					break;
			
				case "S":
				case "s":
					driver.findElement(By.id("alpha_19")).click();
					break;
				
				case "W":
				case "w":	
					driver.findElement(By.id("alpha_23")).click();
					break;
			
			}//End switch case	

			Generic.explicitWait(3);
			
			//Get the WebTable handle
			WebElement element=driver.findElement(By.xpath("//table[@class='lvt small']"));
			//Retrieve all the rows
			List<WebElement> rowCollection=element.findElements(By.xpath("//*[@class='lvt small']/tbody/tr"));
			//System.out.println("Number of rows in this table: "+rowCollection.size());
			int rowSize=rowCollection.size();
		
			//Declare an Array and set the arraysize as rowsize-2 as the first two rows are headers which do not contain user data
			String[] array1;
			array1 = new String[rowSize-2];
			
			//Check for 'No Lead Found' in try block and 'Lead Found' in catch block
			try {
				driver.findElement(By.xpath("//span[@class='genHeaderSmall']")).isDisplayed();
				array1[0]="Lead Not found !";
			} 
			catch (NoSuchElementException e) {
				if(rowSize > 2) {
					int n=0;
					for (int j = 2; j <rowSize; j++) {
						List<WebElement> colCollection=rowCollection.get(j).findElements(By.xpath("td"));
						array1[n]=colCollection.get(2).getText();
						n++;
					}//End for

					//Check if the first letter of the retrieved lead name matches the search character
					if(array1[0].substring(0,1).equalsIgnoreCase(searchChar))
						Reporter.log("Search matches",true);
				}//end if
			}// End catch block
			return array1;
		}//End Method	 
			 
		
		//8.Add Modules to be displayed on Main menu using Menu Editor.The top 10 items appear on the main menu and delete it after verification.
		public static void changeMenu(String module){
			//Build the Action class to Mouseover(Hover) on the Settings button present next to'Administrator'
			WebElement crmSettings=driver.findElement(By.cssSelector("img[src$='mainSettings.PNG']"));
			Actions actions = new Actions(driver);
			actions.moveToElement(crmSettings).build().perform();
			
			// Navigate to Menu Editor
			driver.findElement(By.linkText("CRM Settings")).click();
			driver.findElement(By.linkText("Menu Editor")).click();
		
			//Select the required module
			WebElement listBox = driver.findElement(By.id("availList"));
			Select mSelect = new Select(listBox);
			mSelect.selectByVisibleText(module);
			
			//Send the selected module to right side box
			driver.findElement(By.xpath("//img[@title='right']")).click();
			
			//Set the selected module to be in Top 10 list to make it appear on the main menu
			for (int i = 0; i <= 3; i++) {
				driver.findElement(By.xpath("//img[@title='up']")).click();
			}
			driver.findElement(By.xpath("//input[@name='save']")).click();
			Generic.explicitWait(2);
			
			//Refresh the page by clicking anywhere on the page
			ProjectSpecific.gotoModule("Leads");
			Generic.explicitWait(3);
			
			//Verify if the selected module appears on the main page
			ProjectSpecific.gotoModule(module);
		}
		
		//9. Schedule a meeting for today using Calendar button on home page. 
		//If the meeting falls on Sat/Sun then schedule the meeting on next working day. 
		public void createMeeting(){
			//Create an instance of Calendar class
			Calendar cal = Calendar.getInstance();
			//Find the current month
			int monthNow = cal.get(Calendar.MONTH);
			//Find the current day
			int curDay=cal.get(Calendar.DAY_OF_WEEK);
		
			int temp;
			String dateToSelect=null,dd = null, mtStartHr=null;

		//If the current day is  Sat/Sun then add 2/1 day(s) to schedule the meeting on a weekday
			if (curDay == 1) {  
				cal.add(Calendar.DATE, 1);
				
				Date date = cal.getTime();             
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
				dateToSelect = format1.format(date);   
				dd=dateToSelect.substring(8);
				
				cal.add(Calendar.HOUR,1);
				temp=cal.get(Calendar.HOUR);
				if (temp <=9) {
					mtStartHr=Integer.toString(temp);
					mtStartHr="0".concat(mtStartHr);
				}
				else {
					mtStartHr=Integer.toString(temp);
				}
			}	
			else if (curDay == 7){  //DAY_OF_WEEK returns 7 if it is Saturday
				cal.add(Calendar.DATE, 2);
				
				Date date = cal.getTime();             
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
				dateToSelect = format1.format(date);   
				dd=dateToSelect.substring(8);

				cal.add(Calendar.HOUR,1);
				temp=cal.get(Calendar.HOUR);
				if (temp <=9) {
					mtStartHr=Integer.toString(temp);
					mtStartHr="0".concat(mtStartHr);
				}
				else {
					mtStartHr=Integer.toString(temp);
				}
			}	
			else{	
				//Add one hour to the day
				cal.add(Calendar.HOUR_OF_DAY,1);
				
				if(curDay != 1 &&  curDay!=7){
					if((cal.get(Calendar.HOUR_OF_DAY) == 23) && curDay==6){
						cal.add(Calendar.DATE, 2);
					}
								
				Date date = cal.getTime();             
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
				dateToSelect = format1.format(date);   
				dd=dateToSelect.substring(8);
					
				temp=cal.get(Calendar.HOUR);
				if (temp <=9) {
					mtStartHr=Integer.toString(temp);
					mtStartHr="0".concat(mtStartHr);
				}
				else {
					mtStartHr=Integer.toString(temp);
				}
				
				}
			}
			
			//Get the current month to find out if the month has changed after adding 2/1 day(s) 
			int monthAfter = cal.get(Calendar.MONTH);
			//cal.add(Calendar.MONTH,2);

			driver.findElement(By.cssSelector("img[title='Open Calendar...']")).click();
			Generic.explicitWait(3);
						
			//If current month has changed then click next month on calendar
			if(monthNow != monthAfter){
				driver.findElement(By.cssSelector("img[src$='small_right.gif']")).click();
				Generic.explicitWait(3);
			}		
			
			//select the date using the string extracted above
			driver.findElement(By.linkText(dd)).click();
		
			//Fetch the element on whom the mouse should be hovered
			WebElement addMeet =driver.findElement(By.cssSelector("td.calAddButton"));
			//Create instance of Actions class and pass the WebDriver as argument
			Actions actions = new Actions(driver);
			actions.moveToElement(addMeet).build().perform();
			driver.findElement(By.id("addmeeting")).click();

			driver.findElement(By.cssSelector("input[name='subject']")).sendKeys("Team Meeting");
			driver.findElement(By.xpath("//input[@type='checkbox' and @name='visibility']")).click();
			
			
			Select tSelect; 
			tSelect = new Select(driver.findElement(By.id("eventstatus")));
			tSelect.selectByValue("Planned");
		
			Generic.explicitWait(2);
			
			tSelect = new Select(driver.findElement(By.id("starthr")));
			tSelect.selectByValue(mtStartHr);
		
			Generic.explicitWait(2);
			
			driver.findElement(By.id("jscal_field_date_start")).clear();
			driver.findElement(By.id("jscal_field_date_start")).sendKeys(dateToSelect);
					
			Generic.explicitWait(2);
			
			driver.findElement(By.cssSelector("input[name='eventsave']")).click();
			
			Generic.explicitWait(3);
			/*//Handle the Child Browser
			Iterator <String> allWH	= driver.getWindowHandles().iterator();
			String parentWH = allWH.next();
			String childWH =  allWH.next();
			driver.switchTo().window(childWH);
			
			//Transfer the control back to Parent Browser
			driver.switchTo().window(parentWH);
			
			*
			*/
		}
		
		
		
		
		//10.
		
		
		//17.Verify Title()
		public static void verifyTitle(String eTitle)
		{
			Generic.explicitWait(3);
			String aTitle = driver.getTitle();
			Assert.assertEquals(aTitle, eTitle);
		}

		//8.Verify Error Message
			public static void verifyErrMsg(String eErrMsg)
			{
				String aErrMsg = driver.findElement(By.className("errormsg")).getText();
				Assert.assertEquals(aErrMsg, eErrMsg);
			}

		//9.Verify Success Message
		public static void verifySuccessMsg(String eSuccessMsg)
		{
			String aSuccessMsg=driver.findElement(By.className("successmsg")).getText();
			Assert.assertEquals(aSuccessMsg, eSuccessMsg);
		}
} //End of ProjectSpecific class

package com.vTiger.Library;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
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
			//Find the element needed to build the Action class to Mouseover(Hover) on the icon which has Sign-Out button
			WebElement signout =driver.findElement(By.cssSelector("img[src$='user.PNG']"));
			//Create an instance of the myActionClass which does the Mouseover(Hover) action
			MyActionClass aRef=new MyActionClass();
			aRef.myActionClass(driver, signout);
		
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
			
			MySelectClass mySelClass = new MySelectClass();
			mySelClass.prepareSelect(driver, "name", "salutationtype");
			mySelClass.mySelectClassByValue(title);
			
			driver.findElement(By.name("firstname")).sendKeys(firstName);
			driver.findElement(By.name("lastname")).sendKeys(lastName);
			driver.findElement(By.name("company")).sendKeys(company);
			
			mySelClass.prepareSelect(driver, "name", "industry");
			mySelClass.mySelectClassByValue(industry);
			
			if (assignedTo.equals("User")){
				driver.findElement(By.xpath("//input[@value='U']")).click();
				mySelClass.prepareSelect(driver, "name", "assigned_user_id");
				mySelClass.mySelectClassByIndex(0);
			} 
			else if (assignedTo.equals("Group")){
				driver.findElement(By.xpath("//input[@value='T']")).click();
				mySelClass.prepareSelect(driver, "name", "assigned_group_id");
				mySelClass.mySelectClassByIndex(1);
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
				MyChildBrowserclass childRef = new MyChildBrowserclass();
				String[] res=childRef.myGetWindowHandles(driver);

				childRef.switchToChild(driver, res[1]);
				//driver.findElement(By.xpath("//tr[2]/td[1]/input[@name='selected_id']")).click();
				driver.findElement(By.xpath("//tr[2]/td[2]/a")).click();
								
				//Transfer the control back to Parent Browser
				childRef.switchToChild(driver, res[0]);
				
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
			//Find the element needed to build the Action class to Mouseover(Hover) on the Settings button present next to'Administrator'
			WebElement crmSettings=driver.findElement(By.cssSelector("img[src$='mainSettings.PNG']"));
			//Create an instance of the myActionClass which does the Mouseover(Hover) action
			MyActionClass aRef=new MyActionClass();
			aRef.myActionClass(driver, crmSettings);

			// Navigate to Menu Editor
			driver.findElement(By.linkText("CRM Settings")).click();
			driver.findElement(By.linkText("Menu Editor")).click();
		
			//Select the required module. Create mySelect instance
		/*	MySelectClass mySelClass = new MySelectClass();
			mySelClass.mySelectClassByVisibleText(driver,"id","availList", module);
		*/	
			MySelectClass mySelClass = new MySelectClass();
			mySelClass.prepareSelect(driver,"id","availList");
			mySelClass.mySelectClassByVisibleText( module);

			
			//Send the selected module to right side box
			driver.findElement(By.xpath("//img[@title='right']")).click();
			
			//Set the selected module to be in Top 10 list to make it appear on the main menu
			for (int i = 0; i <= 3; i++) {
				driver.findElement(By.xpath("//img[@title='up']")).click();
			}
			driver.findElement(By.xpath("//input[@name='save']")).click();
			Generic.explicitWait(2);
			
			//Refresh the page by clicking on Home page button
			driver.findElement(By.cssSelector("img[src$='Home.PNG']")).click();
			Generic.explicitWait(2);
			
			//Verify if the selected module appears on the main page
			ProjectSpecific.gotoModule(module);
				
			//Delete the added module
			crmSettings=driver.findElement(By.cssSelector("img[src$='mainSettings.PNG']"));
			
			aRef.myActionClass(driver, crmSettings);
			// Navigate to Menu Editor
			driver.findElement(By.linkText("CRM Settings")).click();
			driver.findElement(By.linkText("Menu Editor")).click();
		
			//Select the required module. Use the above created mySelect instance
			mySelClass.prepareSelect(driver,"id","selectedColumns");
			mySelClass.mySelectClassByVisibleText( module);

			//Send the selected module to right side box
			driver.findElement(By.xpath("//img[@title='left']")).click();
			driver.findElement(By.xpath("//input[@name='save']")).click();
			driver.findElement(By.cssSelector("img[src$='Home.PNG']")).click();
		}
		
		//9. Schedule a meeting for today using Calendar button on home page. 
		//If the meeting falls on Sat/Sun then schedule the meeting on next working day. 
		public void createMeeting(){
			MyDateTime mydt =	new MyDateTime();

			String	dateToSelect=mydt.checkDay().substring(0,10);
			String mtStartHr=mydt.meetTime();
			String mer=mydt.meetMeridian().toLowerCase();
			String dd=mydt.meetDate();
			int monthNow=mydt.monthNow;
			int monthAfter=mydt.retMonth();
			
			//Convert date to integer to check if the date is between 0-9. If yes, remove '0' from date
			int x = Integer.valueOf(dd);
			if(x <=9)
				dd=mydt.meetDate().substring(1);
			
			driver.findElement(By.cssSelector("img[title='Open Calendar...']")).click();
			Generic.explicitWait(3);
						
			//If current month has changed then click next month on calendar
			if(monthNow != monthAfter){
				driver.findElement(By.cssSelector("img[src$='small_right.gif']")).click();
				Generic.explicitWait(3);
			}		
			
			//select the date using the string extracted above
			driver.findElement(By.linkText(dd)).click();
		
			//Find the element needed to build the Action class to Mouseover(Hover) on the 'Add' button
			WebElement addMeet =driver.findElement(By.cssSelector("td.calAddButton"));
			//Create an instance of the myActionClass which does the Mouseover(Hover) action
			MyActionClass aRef=new MyActionClass();
			aRef.myActionClass(driver, addMeet);
				
			driver.findElement(By.id("addmeeting")).click();
			driver.findElement(By.cssSelector("input[name='subject']")).sendKeys("Team Meeting");
			driver.findElement(By.xpath("//input[@type='checkbox' and @name='visibility']")).click();
			
			MySelectClass mySelClass = new MySelectClass();
			mySelClass.prepareSelect(driver,"id","eventstatus");
			mySelClass.mySelectClassByValue("Planned");
			
			Generic.explicitWait(2);
			
			mySelClass.prepareSelect(driver,"id","starthr");
			mySelClass.mySelectClassByValue(mtStartHr);
			
			Generic.explicitWait(2);

			mySelClass.prepareSelect(driver,"css","select[id='startfmt']");
			mySelClass.mySelectClassByValue(mer);
			
			driver.findElement(By.id("jscal_field_date_start")).clear();
			driver.findElement(By.id("jscal_field_date_start")).sendKeys(dateToSelect);
					
			Generic.explicitWait(2);
			
			driver.findElement(By.cssSelector("input[name='eventsave']")).click();
			
			Generic.explicitWait(3);
		}
		
		//10. Click 'Privacy Policy' and handle the child browsers
		public static void privacyPolicy()
		{
			driver.findElement(By.linkText("Privacy Policy")).click();
			
			MyChildBrowserclass childRef = new MyChildBrowserclass();
			String[] res = childRef.myGetWindowHandles(driver);
	
			childRef.switchToChild(driver, res[1]);
			WebElement contact=driver.findElement(By.xpath("//a[@href='/contact/']"));
			
			MyActionClass actionRef= new MyActionClass();
			actionRef.myContextClick(driver, contact);
			
			res = childRef.myGetWindowHandles(driver);
			
			childRef.switchToChild(driver, res[2]);
			childRef.closeDriver(driver);
			
			childRef.switchToChild(driver, res[1]);
			childRef.closeDriver(driver);
			
			//switch the control back to parent
			childRef.switchToParent(driver, res[0]);
			driver.findElement(By.cssSelector("img[src$='Home.PNG']")).click();

		}
				
		//11.Verify Title()
		public static void verifyTitle(String eTitle)
		{
			Generic.explicitWait(3);
			String aTitle = driver.getTitle();
			Assert.assertEquals(aTitle, eTitle);
		}

		//12.Verify Error Message
			public static void verifyErrMsg(String eErrMsg)
			{
				String aErrMsg = driver.findElement(By.className("errormsg")).getText();
				Assert.assertEquals(aErrMsg, eErrMsg);
			}

		//13.Verify Success Message
		public static void verifySuccessMsg(String eSuccessMsg)
		{
			String aSuccessMsg=driver.findElement(By.className("successmsg")).getText();
			Assert.assertEquals(aSuccessMsg, eSuccessMsg);
		}
} //End of ProjectSpecific class

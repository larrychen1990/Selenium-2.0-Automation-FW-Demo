package com.vTiger.Leads;

import org.testng.annotations.Test;

import com.vTiger.Library.Generic;
import com.vTiger.Library.ProjectSpecific;
import com.vTiger.Library.SuperTestNG;


public class VT_CreateLead_Valid extends SuperTestNG{
	@Test 
	public void testVT_CreateLead_Valid()
	{
		String xlPath ="./testdatas/Leads/VT_Leads_Create.xlsx";
		String sheetName = "sheet1";
		int rc = Generic.getXLRowCount(xlPath, sheetName);
		
		//Read user-name, password and navigate to Leads page
		String un= Generic.getXLCellValue(xlPath, sheetName, 1, 0);
		String pwd = Generic.getXLCellValue(xlPath, sheetName, 1, 1);
		String leadsPage = Generic.getXLCellValue(xlPath, sheetName, 1, 2);
		ProjectSpecific.login(un,pwd);
		ProjectSpecific.gotoModule("Leads");
		ProjectSpecific.verifyTitle(leadsPage);
		
		//Read the excel sheet for Lead details to be created	
		for(int i=1; i<=rc;i++)
		{
			String title= Generic.getXLCellValue(xlPath, sheetName, i, 3);
			String firstName = Generic.getXLCellValue(xlPath, sheetName, i, 4);
			String lastName = Generic.getXLCellValue(xlPath, sheetName, i, 5);
			String company = Generic.getXLCellValue(xlPath, sheetName, i, 6);
			String industry = Generic.getXLCellValue(xlPath, sheetName, i, 7);
			String assignedTo = Generic.getXLCellValue(xlPath, sheetName, i, 8);
			String team = Generic.getXLCellValue(xlPath, sheetName, i, 9);
			
			//Create Lead
			ProjectSpecific.createLead(title,firstName ,lastName ,company,industry ,assignedTo,team);
		}
		
		//ProjectSpecific.logout();

	}
}	
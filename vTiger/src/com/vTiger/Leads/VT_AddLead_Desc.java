package com.vTiger.Leads;


import org.testng.annotations.Test;

import com.vTiger.Library.Generic;
import com.vTiger.Library.ProjectSpecific;
import com.vTiger.Library.SuperTestNG;
	 

public class VT_AddLead_Desc extends SuperTestNG{
	@Test
	public static void testVT_Edit_LeadInfo() {
			ProjectSpecific.login("admin","admin");
			
			String xlPath ="./testdatas/Leads/VT_Add_Leads_Desc.xlsx";
			String sheetName = "sheet1";
			int rc = Generic.getXLRowCount(xlPath, sheetName);

			//Navigate to Leads page
			ProjectSpecific.gotoModule("Leads");

			//Read the excel sheet for Lead names on whom the description needs to be added
			for(int i=1; i<=rc; i++)
			{
				String leadName= Generic.getXLCellValue(xlPath, sheetName, i, 0);
				//Add description	
				ProjectSpecific.addLeadDesc(leadName);
				
				//Go back to Leads page for the next iteration
				ProjectSpecific.gotoModule("Leads");
			}	

			ProjectSpecific.logout();
	} //End of method
} //End of Class
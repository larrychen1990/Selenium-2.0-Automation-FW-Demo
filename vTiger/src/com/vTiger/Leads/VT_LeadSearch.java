package com.vTiger.Leads;

import org.testng.annotations.Test;

import com.vTiger.Library.ProjectSpecific;
import com.vTiger.Library.SuperTestNG;

public class VT_LeadSearch extends SuperTestNG{
	@Test
	public static void testVT_LeadSearch() {
			ProjectSpecific.login("admin","admin");
			ProjectSpecific.gotoModule("Leads");

			//Search the Lead Name by passing the first character of the Last Name
			String[] res =ProjectSpecific.searchLead("s");
			for(String str : res)
		        System.out.println("Lead Last Name returned: "+ str+ "\n");
			
			ProjectSpecific.logout();
	 }
}	

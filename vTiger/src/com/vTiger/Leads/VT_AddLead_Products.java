package com.vTiger.Leads;

import org.testng.annotations.Test;
import com.vTiger.Library.ProjectSpecific;
import com.vTiger.Library.SuperTestNG;



public class VT_AddLead_Products extends SuperTestNG{
	@Test
	public static void testVT_AddProducts() {
		ProjectSpecific.login("admin","admin");
		ProjectSpecific.gotoModule("Leads");

		//Add Product
		ProjectSpecific.addLeadProducts();
		ProjectSpecific.logout();
	}	
}	
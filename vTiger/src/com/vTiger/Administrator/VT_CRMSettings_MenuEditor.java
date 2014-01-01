package com.vTiger.Administrator;

import org.testng.annotations.Test;

import com.vTiger.Library.ProjectSpecific;
import com.vTiger.Library.SuperTestNG;

public class VT_CRMSettings_MenuEditor extends SuperTestNG{
	@Test
	public static void testVT_CRMSettings_MenuEditor() {
		ProjectSpecific.login("admin","admin");
		ProjectSpecific.gotoModule("Leads");
		
		ProjectSpecific.changeMenu("Quotes");
		ProjectSpecific.logout();
		
		
	}
}	
 

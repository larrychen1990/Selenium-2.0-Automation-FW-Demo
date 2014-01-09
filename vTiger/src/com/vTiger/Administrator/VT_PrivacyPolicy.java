package com.vTiger.Administrator;

import org.testng.annotations.Test;

import com.vTiger.Library.ProjectSpecific;
import com.vTiger.Library.SuperTestNG;

public class VT_PrivacyPolicy extends SuperTestNG {
	@Test
	public static void testVT_PrivacyPolicy() {
		ProjectSpecific.login("admin","admin");

		//click on privacy policy
		ProjectSpecific.privacyPolicy();
		
		ProjectSpecific.logout();
	}	

}

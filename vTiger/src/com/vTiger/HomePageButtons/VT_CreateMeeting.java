package com.vTiger.HomePageButtons;

import org.testng.annotations.Test;

import com.vTiger.Library.ProjectSpecific;
import com.vTiger.Library.SuperTestNG;

public class VT_CreateMeeting extends SuperTestNG {
	@Test
	public static void testVT_Calendar(){
		ProjectSpecific.login("admin","admin");
		
		ProjectSpecific myMeet= new ProjectSpecific();
		myMeet.createMeeting();

		ProjectSpecific.logout();
		
	}
}

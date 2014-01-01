package com.vTiger.Login_Logout;

import org.testng.annotations.Test;
import com.vTiger.Library.Generic;
import com.vTiger.Library.ProjectSpecific;
import com.vTiger.Library.SuperTestNG;


	public class VT_Login_Valid extends SuperTestNG {
		@Test(groups={"module1"})
		public static void testVT_Login_Valid()
		{
			String xlPath ="./testdatas/Login/VT_Login_Valid.xlsx";
			String sheetName = "sheet1";
			int rc = Generic.getXLRowCount(xlPath, sheetName);
			for(int i=1; i<=rc;i++)
			{
				String un= Generic.getXLCellValue(xlPath, sheetName, i, 0);
				String pwd = Generic.getXLCellValue(xlPath, sheetName, i, 1);
				String homePage = Generic.getXLCellValue(xlPath, sheetName, i, 2);
				String loginPage=Generic.getXLCellValue(xlPath, sheetName, i, 3);
			
				ProjectSpecific.login(un,pwd);
			    ProjectSpecific.verifyTitle(homePage);
			    ProjectSpecific.logout();
			    ProjectSpecific.verifyTitle(loginPage);
			}
			
		}//End of @Test
	}//End of class

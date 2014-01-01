package com.vTiger.Library;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.testng.annotations.Test;

 

public class test extends SuperTestNG {
	@Test
	public static void testtest() {
	
		Calendar cal = Calendar.getInstance();
		int monthNow = cal.get(Calendar.MONTH);
		int curDay=cal.get(Calendar.DAY_OF_WEEK);

		int  time, temp;
		String dateToSelect, mtStartHr=null;

	//If the current day is  Sat/Sun then add 2/1 day(s) to schedule the meeting on a weekday
		if (curDay == 1) {  
			cal.add(Calendar.DATE, 1);
			temp=cal.get(Calendar.DATE);
			dateToSelect=Integer.toString(temp);
			
			cal.add(Calendar.HOUR,1);
			temp=cal.get(Calendar.HOUR);
			mtStartHr=Integer.toString(temp);
			}	
		else if (curDay == 7){  //DAY_OF_WEEK returns 7 if it is Saturday
			cal.add(Calendar.DATE, 2);
			
			Date date = cal.getTime();             
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			 dateToSelect = format1.format(date);   
			 System.out.println(dateToSelect);		
			 System.out.println(dateToSelect.substring(8));
			 
			 
			cal.add(Calendar.HOUR,1);
			temp=cal.get(Calendar.HOUR);
			mtStartHr=Integer.toString(temp);
			
		}	
			
		//Add one hour to the day
		cal.add(Calendar.HOUR_OF_DAY,1);
		if(curDay != 1 &&  curDay!=7){
			if((cal.get(Calendar.HOUR_OF_DAY) == 23) && curDay==6){
				cal.add(Calendar.DATE, 2);
			}
			
			temp=cal.get(Calendar.HOUR);
			mtStartHr=Integer.toString(temp);
			
		}

		 
		
}
} //End of Class
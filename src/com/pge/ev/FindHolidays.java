package com.pge.ev;
import java.util.*;
public class FindHolidays 
{

	
	public static ArrayList<Calendar> findAllHolidays(int year)
	{
		List<String> holidays = FileReader.readHolidaysFile();
		ArrayList<Calendar> dates = new ArrayList<Calendar>();
		if(holidays.contains("New Year's Day"))
			dates.add(newYearsDayObserved(year));
		if(holidays.contains("President's Day"))
			dates.add(presidentsDayObserved(year));
		if(holidays.contains("Memorial Day"))
			dates.add(memorialDayObserved(year));
		if(holidays.contains("Independence Day"))
			dates.add(independenceDayObserved(year));
		if(holidays.contains("Labor Day"))
			dates.add(laborDayObserved(year));
		if(holidays.contains("Veterans Day"))
			dates.add(veteransDayObserved(year));
		if(holidays.contains("Thanksgiving Day"))
			dates.add(thanksgivingDayObserved(year));
		if(holidays.contains("Christmas Day"))
			dates.add(christmasDayObserved(year));
		return dates;
	}
	public static Calendar presidentsDayObserved (int nYear)
    {
		// Third Monday in February
		int nX;
		int nMonth = 1; // February
		Calendar dtD = Calendar.getInstance();
		dtD.set(nYear, nMonth, 1);
		nX = dtD.get(Calendar.DAY_OF_WEEK);//1-7
		Calendar returnCal = Calendar.getInstance();
		switch(nX)
		{
			case 1 : // Sunday
				returnCal.set(nYear, nMonth, 16);
				return returnCal;
			case 2 : // Monday
				returnCal.set(nYear, nMonth, 15);
				return returnCal;
			case 3 : // Tuesday
				returnCal.set(nYear, nMonth, 21);
				return returnCal;
	        case 4 : // Wednesday
	        	returnCal.set(nYear, nMonth, 20);
	        	return returnCal;
	        case 5 : // Thursday
	        	returnCal.set(nYear, nMonth, 19);
	        	return returnCal;
	        case 6 : // Friday
	        	returnCal.set(nYear, nMonth, 18);
	        	return returnCal;
	        default : // Saturday
	        	returnCal.set(nYear, nMonth, 17);
	        	return returnCal;
        }
    }
	public static Calendar newYearsDayObserved (int nYear)
    {
		// January 1st
		int nX;
		int nMonth = 0; // January
		int nMonthDecember = 11;
		Calendar dtD = Calendar.getInstance();
		dtD.set(nYear, nMonth, 1);
		nX = dtD.get(Calendar.DAY_OF_WEEK);//1-7
		Calendar returnCal = Calendar.getInstance();
		switch(nX)
		{
			case 1 : // Sunday
				returnCal.set(nYear, nMonth, 2);
				return returnCal;
			case 2 : // Monday
			case 3 : // Tuesday
	        case 4 : // Wednesday
	        case 5 : // Thursday
	        case 6 : // Friday
	        	returnCal.set(nYear, nMonth, 1);
	        	return returnCal;
	        default : // Saturday
	        	returnCal.set(--nYear, nMonthDecember, 31);
	        	return returnCal;
        }
    }
	public static Calendar memorialDayObserved (int nYear)
    {
		// Last Monday in May
		int nX;
		int nMonth = 4; // May
		Calendar dtD = Calendar.getInstance();
		dtD.set(nYear, nMonth, 31);
		nX = dtD.get(Calendar.DAY_OF_WEEK);//1-7
		Calendar returnCal = Calendar.getInstance();
		switch(nX)
		{
			case 1 : // Sunday
				returnCal.set(nYear, nMonth, 25);
				return returnCal;
			case 2 : // Monday
				returnCal.set(nYear, nMonth, 31);
				return returnCal;
			case 3 : // Tuesday
				returnCal.set(nYear, nMonth, 30);
				return returnCal;
	        case 4 : // Wednesday
	        	returnCal.set(nYear, nMonth, 29);
	        	return returnCal;
	        case 5 : // Thursday
	        	returnCal.set(nYear, nMonth, 28);
	        	return returnCal;
	        case 6 : // Friday
	        	returnCal.set(nYear, nMonth, 27);
	        	return returnCal;
	        default : // Saturday
	        	returnCal.set(nYear, nMonth, 26);
	        	return returnCal;
        }
    }
	public static Calendar independenceDayObserved (int nYear)
    {
		// July 4
		int nX;
		int nMonth = 6; // July
		Calendar dtD = Calendar.getInstance();
		dtD.set(nYear, nMonth, 4);
		nX = dtD.get(Calendar.DAY_OF_WEEK);//1-7
		Calendar returnCal = Calendar.getInstance();
		switch(nX)
		{
			case 1 : // Sunday
				returnCal.set(nYear, nMonth, 5);
				return returnCal;
			case 2 : // Monday
			case 3 : // Tuesday
	        case 4 : // Wednesday
	        case 5 : // Thursday
	        case 6 : // Friday
	        	returnCal.set(nYear, nMonth, 4);
	        	return returnCal;
	        default : // Saturday
	        	returnCal.set(nYear, nMonth, 3);
	        	return returnCal;
        }
    }
	public static Calendar laborDayObserved (int nYear)
    {
		// First Monday in September
		int nX;
		int nMonth = 8; // September
		Calendar dtD = Calendar.getInstance();
		dtD.set(nYear, nMonth, 1);
		nX = dtD.get(Calendar.DAY_OF_WEEK);//1-7
		Calendar returnCal = Calendar.getInstance();
		switch(nX)
		{
			case 1 : // Sunday
				returnCal.set(nYear, nMonth, 2);
				return returnCal;
			case 2 : // Monday
				returnCal.set(nYear, nMonth, 1);
				return returnCal;
			case 3 : // Tuesday
				returnCal.set(nYear, nMonth, 7);
				return returnCal;
	        case 4 : // Wednesday
	        	returnCal.set(nYear, nMonth, 6);
	        	return returnCal;
	        case 5 : // Thursday
	        	returnCal.set(nYear, nMonth, 5);
	        	return returnCal;
	        case 6 : // Friday
	        	returnCal.set(nYear, nMonth, 4);
	        	return returnCal;
	        default : // Saturday
	        	returnCal.set(nYear, nMonth, 3);
	        	return returnCal;
        }
    }
	public static Calendar veteransDayObserved (int nYear)
    {
		//November 11
		int nMonth = 10; // November
		Calendar dtD = Calendar.getInstance();
		dtD.set(nYear, nMonth, 11);
		return dtD;
        
    }
	public static Calendar thanksgivingDayObserved (int nYear)
    {
		// Fourth Thursday in November
		int nX;
		int nMonth = 10; // November
		Calendar dtD = Calendar.getInstance();
		dtD.set(nYear, nMonth, 1);
		nX = dtD.get(Calendar.DAY_OF_WEEK);//1-7
		Calendar returnCal = Calendar.getInstance();
		switch(nX)
		{
			case 1 : // Sunday
				returnCal.set(nYear, nMonth, 26);
				return returnCal;
			case 2 : // Monday
				returnCal.set(nYear, nMonth, 25);
				return returnCal;
			case 3 : // Tuesday
				returnCal.set(nYear, nMonth, 24);
				return returnCal;
	        case 4 : // Wednesday
	        	returnCal.set(nYear, nMonth, 23);
	        	return returnCal;
	        case 5 : // Thursday
	        	returnCal.set(nYear, nMonth, 22);
	        	return returnCal;
	        case 6 : // Friday
	        	returnCal.set(nYear, nMonth, 28);
	        	return returnCal;
	        default : // Saturday
	        	returnCal.set(nYear, nMonth, 27);
	        	return returnCal;
        }
    }
	public static Calendar christmasDayObserved (int nYear)
    {
		// December 25
		int nX;
		int nMonth = 11; // December
		Calendar dtD = Calendar.getInstance();
		dtD.set(nYear, nMonth, 25);
		nX = dtD.get(Calendar.DAY_OF_WEEK);//1-7
		Calendar returnCal = Calendar.getInstance();
		switch(nX)
		{
			case 1 : // Sunday
				returnCal.set(nYear, nMonth, 26);
				return returnCal;
			case 2 : // Monday
			case 3 : // Tuesday
	        case 4 : // Wednesday
	        case 5 : // Thursday
	        case 6 : // Friday
	        	returnCal.set(nYear, nMonth, 25);
	        	return returnCal;
	        default : // Saturday
	        	returnCal.set(nYear, nMonth, 24);
	        	return returnCal;
        }
    }
	
	
	
}

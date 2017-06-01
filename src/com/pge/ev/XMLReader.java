package com.pge.ev;
import java.util.*; 

import javax.xml.parsers.SAXParser; 
import javax.xml.parsers.SAXParserFactory; 

import java.io.*;
public class XMLReader
{

	public static double[] findPeakPartNonUsage(String filename, int startMonth, int startDay, int startYear, int endMonth, int endDay, int endYear, int chargeStartTime, double weekdayDistance, double weekendDistance, double chargingLevel) 
	{
		
		double convertedSummerPeak = 0;
		double convertedSummerPartPeak = 0;
		double convertedSummerNonPeak = 0;
		double convertedWinterPeak = 0;
		double convertedWinterPartPeak = 0;
		double convertedWinterNonPeak = 0;
		double[] returnArray = {0};
		try
		{
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			MyHandler handler = new MyHandler(startMonth, startDay, startYear, endMonth, endDay, endYear);//start and end date
			saxParser.parse(new File(filename), handler);
			
			List<IntervalReading> iRList = handler.getIntervalRList();

			int summerPeak = 0;
			int summerPartPeak = 0;
			int summerNonPeak = 0;
			int winterPeak = 0;
			int winterPartPeak = 0;
			int winterNonPeak = 0;
			int dayOfWeek = 0;
			long longHour = 0;
			
			int counter = 1;
			boolean addEVWeekend = false;
			boolean addEVWeekday = false;
			//Load is determined by distance divided by average efficiency
			double weekdayLoad = weekdayDistance / Tester.AVERAGE_EFFICIENCY;
			double weekendLoad = weekendDistance / Tester.AVERAGE_EFFICIENCY;
			//Amount of time it takes is load divided by the charging level
			double weekdayHours = weekdayLoad / chargingLevel;
			double weekendHours = weekendLoad / chargingLevel;
			double weekdayLoadPerHour = chargingLevel;
			double weekdayLastHourLoad = weekdayLoad - (Math.floor(weekdayHours) * chargingLevel);
			double weekendLoadPerHour = chargingLevel;
			double weekendLastHourLoad = weekendLoad - (Math.floor(weekendHours) * chargingLevel);
			
			//Adds EV for first couple hours because following code doesn't account for overnight usage on first day
			Calendar cal = Calendar.getInstance();
			cal.set(startYear, startMonth - 1, startDay);
			if(cal.get(Calendar.DAY_OF_WEEK) <= 2)//Period starts on Sunday or Monday(accounts for early morning charge after a weekend)
			{
				if((chargeStartTime + weekendHours) > 24)
				{
					double hrsOver = (chargeStartTime + weekendHours) - 24;
					for(int i = 0; i < Math.floor(hrsOver); i++)
					{
						iRList.get(i).setValue(iRList.get(i).getValue() + (int)(weekendLoadPerHour * 1000));
					}
					iRList.get((int)Math.floor(hrsOver)).setValue(iRList.get((int)Math.floor(hrsOver)).getValue() + (int)(weekendLastHourLoad * 1000));
				}
			}
			else//period starts Tuesday-Saturday (early morning charge after a weekday)
			{
				if((chargeStartTime + weekdayHours) > 24)
				{
					double hrsOver = (chargeStartTime + weekdayHours) - 24;
					for(int i = 0; i < Math.floor(hrsOver); i++)
					{
						iRList.get(i).setValue(iRList.get(i).getValue() + (int)(weekdayLoadPerHour * 1000));
					}
					iRList.get((int)Math.floor(hrsOver)).setValue(iRList.get((int)Math.floor(hrsOver)).getValue() + (int)(weekdayLastHourLoad * 1000));
				}
			}
			
			
			String[][] dayHoursSummer = FileReader.readSummerPeakTimes();
			String[][] dayHoursWinter = FileReader.readWinterPeakTimes();
			String[][] dayHoursSummerDST = FileReader.readSummerPeakTimesDST();
			String[][] dayHoursWinterDST = FileReader.readWinterPeakTimesDST();
			boolean isHoliday = false;
			boolean isDaylightSavings;
			boolean isSummer = false;
			
			
			//Adds in projected EV usage
			for (IntervalReading ir: iRList)
			{
				longHour = ir.getHour();
				int hour = (int) longHour;//0-23 hr
				Calendar date = ir.getDate();
				isSummer = findIsSummer(date);
				dayOfWeek = ir.getDate().get(Calendar.DAY_OF_WEEK) - 1;//-1 accounts for the array that goes 0-6 whereas the calendar goes 1-7
				isHoliday = findIsHoliday(date);
				isDaylightSavings = findIsDaylightSavings(date);
				if((dayOfWeek== 0 || dayOfWeek == 6) && hour == chargeStartTime)
				{
					addEVWeekend = true;
				}
				if((dayOfWeek >= 1 && dayOfWeek <= 5) && hour == chargeStartTime)
				{
					addEVWeekday = true;
				}
				if(addEVWeekend)
				{
					if(counter <=  Math.floor(weekendHours))
					{
						counter += 1;
						ir.setValue(ir.getValue() + (int)(weekendLoadPerHour * 1000));
					}
					else
					{
						counter = 1;
						addEVWeekend = false;
						ir.setValue(ir.getValue() + (int)(weekendLastHourLoad * 1000));
					}
				}
				if(addEVWeekday)
				{
					if(counter <=  Math.floor(weekdayHours))
					{
						counter += 1;
						ir.setValue(ir.getValue() + (int)(weekdayLoadPerHour * 1000));
					}
					else
					{
						counter = 1;
						addEVWeekday = false;
						ir.setValue(ir.getValue() + (int)(weekdayLastHourLoad * 1000));
					}
				}
				
				if(isSummer)
				{
					
					if(isDaylightSavings)
					{
						
						if (dayHoursSummerDST[dayOfWeek][hour].equalsIgnoreCase("peak"))
						{
							summerPeak += ir.getValue();
						}
						else if(dayHoursSummerDST[dayOfWeek][hour].equalsIgnoreCase("part"))
						{
							summerPartPeak += ir.getValue();
						}
						else//Off
						{
							summerNonPeak += ir.getValue();
						}
					}
					else//not DST
					{
						if (dayHoursSummer[dayOfWeek][hour].equalsIgnoreCase("peak"))
						{
							summerPeak += ir.getValue();
						}
						else if(dayHoursSummer[dayOfWeek][hour].equalsIgnoreCase("part"))
						{
							summerPartPeak += ir.getValue();
						}
						else//Off
						{
							summerNonPeak += ir.getValue();
						}
					}
				}
				else//winter
				{
					if(isHoliday)
					{
						winterNonPeak += ir.getValue();
					}
					else if(isDaylightSavings)
					{
						if(dayHoursWinterDST[dayOfWeek][hour].equalsIgnoreCase("peak"))
						{
							winterPeak += ir.getValue();
						}
						else if(dayHoursWinterDST[dayOfWeek][hour].equalsIgnoreCase("part"))
						{
							winterPartPeak += ir.getValue();
						}
						else//Off
						{
							winterNonPeak += ir.getValue();
						}
					}
					else//Not DST
					{
						if(dayHoursWinter[dayOfWeek][hour].equalsIgnoreCase("peak"))
						{
							winterPeak += ir.getValue();
						}
						else if(dayHoursWinter[dayOfWeek][hour].equalsIgnoreCase("part"))
						{
							winterPartPeak += ir.getValue();
						}
						else//Off
						{
							winterNonPeak += ir.getValue();
						}
					}
					
				}
				
				
			}
			
			convertedSummerPeak = summerPeak * .001;//converting to kWh instead of Wh
			convertedSummerPartPeak = summerPartPeak * .001;
			convertedSummerNonPeak = summerNonPeak * .001;
			convertedWinterPeak = winterPeak * .001;
			convertedWinterPartPeak = winterPartPeak * .001;
			convertedWinterNonPeak = winterNonPeak * .001;
			returnArray = new double[]{convertedSummerPeak, convertedSummerPartPeak, convertedSummerNonPeak, convertedWinterPeak, convertedWinterPartPeak, convertedWinterNonPeak};
			return returnArray;			
			
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			System.out.println("Problem Reading XML File");
		}
		return returnArray;
	}	
	
	public static boolean findIsSummer(Calendar date)
	{
		int year = date.get(Calendar.YEAR);
		Calendar date1 = Calendar.getInstance();
		Calendar date2 = Calendar.getInstance();
		date1.set(year, 4, 1);
		date1.set(Calendar.HOUR_OF_DAY, 0);
		date1.set(Calendar.MINUTE, 0);
		date1.set(Calendar.SECOND, 0);
		date1.set(Calendar.MILLISECOND, 0);
		date2.set(year, 9, 31);
		date2.set(Calendar.HOUR_OF_DAY, 0);
		date2.set(Calendar.MINUTE, 0);
		date2.set(Calendar.SECOND, 0);
		date2.set(Calendar.MILLISECOND, 0);
		return !(date.before(date1) || date.after(date2));
	}
	public static boolean findIsHoliday(Calendar date)
	{
		ArrayList<Calendar> dates = FindHolidays.findAllHolidays(date.get(Calendar.YEAR));
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		boolean isHoliday = false;
		for(Calendar day: dates)
		{
			day.set(Calendar.HOUR_OF_DAY, 0);
			day.set(Calendar.MINUTE, 0);
			day.set(Calendar.SECOND, 0);
			day.set(Calendar.MILLISECOND, 0);
			if(day.equals(date))
			{
				isHoliday = true;
			}
		}
		return isHoliday;
	}
	public static boolean findIsDaylightSavings(Calendar date)
	{
		int year = date.get(Calendar.YEAR);
		Calendar date1 = Calendar.getInstance();
		Calendar date2 = Calendar.getInstance();
		Calendar date3 = Calendar.getInstance();
		Calendar date4 = Calendar.getInstance();
		int day1 = findSecondSundayMarch(year);
		int day2 = findFirstSundayApril(year);
		int day3 = findLastSundayOctober(year);
		int day4 = findFirstSundayNovember(year);
		date1.set(year, 2, day1);
		date1.set(Calendar.HOUR_OF_DAY, 0);
		date1.set(Calendar.MINUTE, 0);
		date1.set(Calendar.SECOND, 0);
		date1.set(Calendar.MILLISECOND, 0);
		date2.set(year, 3, day2);
		date3.set(year, 9, day3);
		date3.set(Calendar.HOUR_OF_DAY, 0);
		date3.set(Calendar.MINUTE, 0);
		date3.set(Calendar.SECOND, 0);
		date3.set(Calendar.MILLISECOND, 0);
		date4.set(year, 10, day4);
		return ((!date.before(date1)) && date.before(date2)) || ((!date.before(date3)) && date.before(date4));//inclusive on first Sunday and exclusive for last Sunday.
	}
	public static int findSecondSundayMarch(int year)
	{
		int nX;
		int nMonth = 2; // March
		Calendar dtD = Calendar.getInstance();
		dtD.set(year, nMonth, 1);//gets day of week of first day of month
		nX = dtD.get(Calendar.DAY_OF_WEEK);//1-7
		switch(nX)
		{
		case 1 : // Sunday
			return 8;
		case 2 : // Monday
			return 14;
		case 3 : // Tuesday
			return 13;
        case 4 : // Wednesday
        	return 12;
        case 5 : // Thursday
        	return 11;
        case 6 : // Friday
        	return 10;
        default : // Saturday
        	return 9;
		}
	}
	public static int findFirstSundayApril(int year)
	{
		int nX;
		int nMonth = 3; // April
		Calendar dtD = Calendar.getInstance();
		dtD.set(year, nMonth, 1);//gets day of week of first day of month
		nX = dtD.get(Calendar.DAY_OF_WEEK);//1-7
		switch(nX)
		{
		case 1 : // Sunday
			return 1;
		case 2 : // Monday
			return 7;
		case 3 : // Tuesday
			return 6;
        case 4 : // Wednesday
        	return 5;
        case 5 : // Thursday
        	return 4;
        case 6 : // Friday
        	return 3;
        default : // Saturday
        	return 2;
		}
	}
	public static int findLastSundayOctober(int year)
	{
		int nX;
		int nMonth = 9; // October
		Calendar dtD = Calendar.getInstance();
		dtD.set(year, nMonth, 1);//gets day of week of first day of month
		nX = dtD.get(Calendar.DAY_OF_WEEK);//1-7
		switch(nX)
		{
		case 1 : // Sunday
			return 29;
		case 2 : // Monday
			return 28;
		case 3 : // Tuesday
			return 27;
        case 4 : // Wednesday
        	return 26;
        case 5 : // Thursday
        	return 25;
        case 6 : // Friday
        	return 31;
        default : // Saturday
        	return 30;
		}
	}
	public static int findFirstSundayNovember(int year)
	{
		int nX;
		int nMonth = 10; // November
		Calendar dtD = Calendar.getInstance();
		dtD.set(year, nMonth, 1);//gets day of week of first day of month
		nX = dtD.get(Calendar.DAY_OF_WEEK);//1-7
		switch(nX)
		{
		case 1 : // Sunday
			return 1;
		case 2 : // Monday
			return 7;
		case 3 : // Tuesday
			return 6;
        case 4 : // Wednesday
        	return 5;
        case 5 : // Thursday
        	return 4;
        case 6 : // Friday
        	return 3;
        default : // Saturday
        	return 2;
		}
	}
}

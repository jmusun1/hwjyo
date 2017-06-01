package com.pge.e6;
import java.util.Calendar;
public class BaselineQuantity 
{
   
	
	public static long[] calcNumDays(int startMonth, int startDay, int startYear, int endMonth, int endDay, int endYear)//returns array with summer, winter days
	{
		//Summer is May 1- October 31
		//Winter is November 1- April 30
		//Number of summer days = 184
		//Number of winter days = 181 (182 with leap year)
		
		long summerDays = 0;
		long winterDays = 0;
		
		//Most of this code might be useless if we only have to find a month at a time
		if(startYear != endYear)//9 possible scenarios when start and end years are not the same.
		{
			if(endYear - startYear >= 2)//adds any years in between start and end years
			{
				for(int i = 1; i < endYear - startYear; i++)
				{
					winterDays += calcWinter(1, 1, startYear + i, 4, 30, startYear + i);
					summerDays += calcFullSummer();
					winterDays += calcWinter(11,1,startYear + i, 12, 31, startYear + i);
				}
			}
			if(startMonth <= 4)//start winter
			{
				if(endMonth <= 4)//end in winter
				{
					winterDays += calcWinter(startMonth, startDay, startYear, 4, 30, startYear);
					summerDays += calcFullSummer();
					winterDays += calcWinter(11, 1, endYear - 1, endMonth, endDay, endYear);//allows for leap year
				}
				else if(endMonth >= 5 && endMonth <= 10)//end in summer
				{
					winterDays += calcWinter(startMonth, startDay, startYear, 4, 30, startYear);
					summerDays += calcFullSummer();
					winterDays += calcFullWinter(endYear - 1);
					summerDays += calcSummer(5, 1, endYear, endMonth, endDay, endYear);//HELP
				}
				else//end in year-end winter
				{
					winterDays += calcWinter(startMonth, startDay, startYear, 4, 30, startYear);
					summerDays += calcFullSummer();
					winterDays += calcFullWinter(endYear - 1);
					summerDays += calcFullSummer();
					winterDays += calcWinter(11, 1, endYear, endMonth, endDay, endYear);
				}
			}
			else if(startMonth >=5 && startMonth <= 10)//start in summer
			{
				if(endMonth <= 4)//end in winter
				{
					summerDays += calcSummer(startMonth, startDay, startYear, 10, 31, startYear);
					winterDays += calcWinter(11, 1, endYear - 1, endMonth, endDay, endYear);
				}
				else if(endMonth >= 5 && endMonth <= 10)//end in summer
				{
					summerDays += calcSummer(startMonth, startDay, startYear, 10, 31, startYear);
					winterDays += calcFullWinter(endYear - 1);
					summerDays += calcSummer(5, 1, endYear, endMonth, endDay, endYear);
				}
				else//end in year-end winter
				{
					summerDays += calcSummer(startMonth, startDay, startYear, 10, 31, startYear);
					winterDays += calcFullWinter(endYear - 1);
					summerDays += calcFullSummer();
					winterDays += calcWinter(11, 1, endYear, endMonth, endDay, endYear);
				}
			}
			else//start in year-end winter
			{
				if(endMonth <= 4)//end in winter
				{
					winterDays = calcWinter(startMonth, startDay, endYear - 1, endMonth, endDay, endYear);
				}
				else if(endMonth >= 5 && endMonth <= 10)//end in summer
				{
					winterDays += calcFullWinter(endYear - 1);
					summerDays += calcSummer(5, 1, endYear, endMonth, endDay, endYear);
					
				}
				else//end in year-end winter
				{
					winterDays += calcFullWinter(endYear - 1);
					summerDays += calcFullSummer();
					winterDays += calcWinter(11, 1, endYear, endMonth, endDay, endYear);
				}
			}
		}
		else //Start and End year are the SAME
		{
			if(startMonth <= 4 || startMonth >= 11)//start in winter
			{
				if(endMonth <= 4 || endMonth >= 11)//end in winter
				{
					winterDays += calcWinter(startMonth, startDay, startYear, endMonth, endDay, endYear);
				}
				else if(endMonth >= 5 && endMonth <= 10)//end in summer
				{
					winterDays += calcWinter(startMonth, startDay, startYear, 4, 30, endYear);
					summerDays += calcSummer(5, 1,startYear, endMonth, endDay, endYear);
				}
			}
			else if (startMonth >= 5 && startMonth <= 10)//start in summer
			{
				if(endMonth >= 5 && endMonth <= 10)//end in summer
				{
					summerDays += calcSummer(startMonth, startDay, startYear, endMonth, endDay, endYear);
				}
				else if(endMonth <= 4 || endMonth >= 11)//end in winter
				{
					summerDays += calcSummer(startMonth, startDay, startYear, 10, 31, endYear);
					winterDays += calcWinter(11, 1, startYear, endMonth, endDay, endYear);
				}
			}
		}
		
		long[] returnValue = new long[2];
		returnValue[0] = summerDays;
		returnValue[1] = winterDays;
		return returnValue;
	}
	
	public static long calcWinter(int startMonth, int startDay, int startYear, int endMonth, int endDay, int endYear)
	{
		return diffFindDays(startMonth, startDay, startYear, endMonth, endDay, endYear);
	}
	public static long calcSummer(int startMonth, int startDay, int startYear, int endMonth, int endDay, int endYear)
	{
		return diffFindDays(startMonth, startDay, startYear, endMonth, endDay, endYear);
	}
	
	public static long calcFullSummer()
	{
		return diffFindDays(5,1,1,10,31,1);
	}
	public static long calcFullWinter(int startYear)
	{
		return diffFindDays(11, 1, startYear, 4, 30, startYear + 1);
	}
	
		
	public static long diffFindDays(int startMonth, int startDay, int startYear, int endMonth, int endDay, int endYear)
	{
		Calendar calendar1 = Calendar.getInstance();//Accounts for leap year, but not daylight savings
		Calendar calendar2 = Calendar.getInstance();
		calendar1.set(startYear, startMonth - 1, startDay,0,0);//year,month,day,hour,minute
		calendar2.set(endYear, endMonth - 1, endDay,24,0);
		long milsecs1 = calendar1.getTimeInMillis();
		long milsecs2 = calendar2.getTimeInMillis();
		long diff = milsecs2 - milsecs1;
		long ddays = diff / (24 * 60 * 60 * 1000);
		
		return ddays;
	}	
}

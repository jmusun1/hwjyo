package com.pge.e1;
import java.util.*; 

import javax.xml.parsers.SAXParser; 
import javax.xml.parsers.SAXParserFactory; 

import com.pge.e1.IntervalReading;
import com.pge.rateCompare.NotInBillPeriodException;

import java.io.*;
public class XMLReader
{
	public static double[] findUsage(String filename, int startMonth, int startDay, int startYear, int endMonth, int endDay, int endYear) throws NotInBillPeriodException
	{
		return findUsage(filename, startMonth, startDay, startYear, endMonth, endDay, endYear, 0, 0, 0, 0);
	}
	public static double[] findUsage(String filename, int startMonth, int startDay, int startYear, int endMonth, int endDay, int endYear, double chargingLevel, double weekdayDistance, double weekendDistance, int chargeStartTime) throws NotInBillPeriodException 
	{
		
		double summerValuekWh = 0;
		double winterValuekWh = 0;
		double[] returnArray = {0};
		try
		{
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			MyHandler handler = new MyHandler(startMonth, startDay, startYear, endMonth, endDay, endYear);//start and end date
			saxParser.parse(new File(filename), handler);
			
			List<IntervalReading> iRList = handler.getIntervalRList();
			long summerValue = 0;
			long winterValue = 0;
			int dayOfWeek = 0;
			long longHour = 0;
			
			int counter = 1;
			boolean addEVWeekend = false;
			boolean addEVWeekday = false;
			double weekdayLoad = weekdayDistance / com.pge.ev.Tester.AVERAGE_EFFICIENCY;
			double weekendLoad = weekendDistance / com.pge.ev.Tester.AVERAGE_EFFICIENCY;
			double weekdayHours = weekdayLoad / chargingLevel;
			double weekendHours = weekendLoad / chargingLevel;
			double weekdayLoadPerHour = chargingLevel;
			double weekdayLastHourLoad = weekdayLoad - (Math.floor(weekdayHours) * chargingLevel);
			double weekendLoadPerHour = chargingLevel;
			double weekendLastHourLoad = weekendLoad - (Math.floor(weekendHours) * chargingLevel);
			
			
			if(iRList.isEmpty())
			{
				
				throw new NotInBillPeriodException();
			}
			
			Calendar endCal = Calendar.getInstance();
			endCal.set(endYear, endMonth - 1, endDay);
			if(iRList.get(iRList.size()-1).getDate().before(endCal))
			{
				throw new NotInBillPeriodException();
			}
			
			
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
			
		
			//Adds in projected EV usage
			boolean isSummer = false;
			for (IntervalReading ir: iRList)
			{
				longHour = ir.getHour();
				int hour = (int) longHour;
				dayOfWeek = ir.getDate().get(Calendar.DAY_OF_WEEK) - 1;
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
				
				Calendar date = ir.getDate();
				isSummer = findIsSummer(date);
				if(isSummer)
				{
					
					summerValue += ir.getValue();
				}
				else
				{
					winterValue += ir.getValue();
				}
				
			}
			
			
			summerValuekWh = summerValue * .001;//converting to kWh instead of Wh
			winterValuekWh = winterValue * .001;//converting to kWh instead of Wh
			returnArray = new double[]{summerValuekWh,winterValuekWh};
			return returnArray;			
			
		}
		catch(NotInBillPeriodException e)
		{
			throw new NotInBillPeriodException();
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
		date2.set(Calendar.HOUR_OF_DAY, 23);
		date2.set(Calendar.MINUTE, 59);
		date2.set(Calendar.SECOND, 59);
		date2.set(Calendar.MILLISECOND, 0);
		return !(date.before(date1) || date.after(date2));
	}
}

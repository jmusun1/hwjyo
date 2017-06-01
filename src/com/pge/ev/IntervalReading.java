package com.pge.ev;
import java.util.*;

class IntervalReading
{
	
	int value;
	int duration;
	int cost;
	long start;
	Calendar date;
	long hour;
	public IntervalReading()
	{
		
		this.value = 0;
		this.duration = 0;
		this.cost = 0;
		this.start = 0;
		this.hour = 0;
	}
	public void setCost(int cost)
	{
		this.cost = cost;
	}
	public void setDuration(int duration)
	{
		this.duration = duration;
	}
	
	public void setValue(int value)
	{
		this.value = value;
	}
	public void setStart(long start)
	{
		this.start = start;
	}
	public long getStart()
	{
		return start;
	}
	public String toString()
	{
		return Integer.toString(value);
	}
	
	public int getValue()
	{
		return value;
	}
	public int getCost()
	{
		return cost;
	}
	public void setDate()
	{
		this.date = convertTimestamp(start);
		this.hour = date.get(Calendar.HOUR_OF_DAY);//24 HOUR CLOCK
	}
	public Calendar getDate()
	{
		return date;
	}
	public long getHour()
	{
		return hour;
	}
	
	public static Calendar convertTimestamp(long timestamp)
	{
		Date d = new Date(timestamp + 3600 * 8);//adjust for timezone
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		return cal;
	}
}
package com.pge.e1;
import java.util.Calendar;
import java.util.Date;


class IntervalReading
{
	int cost;
	int duration;
	int value;
	long start;
	Calendar date;
	long hour;
	public IntervalReading()
	{
		this.cost = 0;
		this.duration = 0;
		this.value = 0;
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
		this.hour = date.get(Calendar.HOUR_OF_DAY);//24 Hour clock
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
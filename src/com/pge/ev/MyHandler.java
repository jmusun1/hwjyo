package com.pge.ev;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class MyHandler extends DefaultHandler
{
	List<IntervalReading> list = new ArrayList<>();
	IntervalReading intervalR = null;
	
	public List<IntervalReading> getIntervalRList()
	{
		return list;
	}
	public MyHandler(int startMonth, int startDay, int startYear, int endMonth, int endDay, int endYear)
	{
		this.startMonth = startMonth;
		this.startDay = startDay;
		this.startYear = startYear;
		this.endMonth = endMonth;
		this.endDay = endDay;
		this.endYear = endYear;
		
		Calendar cal = Calendar.getInstance();
		cal.set(startYear, startMonth, startDay);
		
		this.startTime = timeStampConverter(startMonth, startDay, startYear, 0);
		this.endTime = timeStampConverter(endMonth, endDay, endYear, 23) + 3600;//Add the 3600 because of the format of the file so that it goes to the next hour
		
	}
	
	int startMonth;
	int startDay;
	int startYear;
	int endMonth;
	int endDay;
	int endYear;
	long startTime;
	long endTime;
	
	StringBuilder currentText = new StringBuilder();//characters() is called multiple times in a single element so you have to build the string that is inside of it.
	String content = null;
	boolean bvalue = false;
	boolean bstart = false;
	boolean bduration = false;//I don't keep track of it
	boolean bcost = false; 
	boolean startReading = false;
	
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
	{
		if(qName.equalsIgnoreCase("IntervalReading"))
		{
			intervalR = new IntervalReading();
			if(list == null)
			{
				list = new ArrayList<>();
			}
		}
		else if(qName.equalsIgnoreCase("value") && startReading)
		{
			bvalue = true;
		}
		else if(qName.equalsIgnoreCase("duration") && startReading)
		{
			
		}
		else if(qName.equalsIgnoreCase("start"))
		{
			bstart = true;
		}
		else if(qName.equalsIgnoreCase("cost") && startReading)
		{
			bcost = true;
		}
			
	}
	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		if(qName.equalsIgnoreCase("IntervalReading") && startReading)
		{
			intervalR.setDate();
			list.add(intervalR);
		}
		else if(bstart)
		{
			final String content = currentText.toString().trim();
			if(Integer.parseInt(content) == startTime)
			{
				startReading = true;
			}
			if(Integer.parseInt(content) == endTime)
			{
				startReading = false;
			}
			if(intervalR != null)
			{
				intervalR.setStart(Long.parseLong(content));
				intervalR.setStart(intervalR.getStart() * 1000);
			}
			
			currentText.setLength(0);
			bstart = false;
		}
		else if(bvalue && startReading)
		{
			final String content = currentText.toString().trim();
			intervalR.setValue(Integer.parseInt(content));
			currentText.setLength(0);
			bvalue = false;
		}
		else if(bduration && startReading)
		{
			final String content = currentText.toString().trim();
			intervalR.setDuration(Integer.parseInt(content));
			currentText.setLength(0);
			bduration = false;
		}
		else if(bcost && startReading)
		{
			final String content = currentText.toString().trim();
			intervalR.setCost(Integer.parseInt(content));
			currentText.setLength(0);
			bcost = false;
		}
	}
	
	public void characters(char ch[], int start, int length) throws SAXException
	{
		if(bstart)
		{
			currentText.append(ch, start, length);
		}
		else if(bvalue && startReading)
		{
			currentText.append(ch, start, length);
		}
		else if(bduration && startReading)
		{
			currentText.append(ch, start, length);
		}
		else if(bstart && startReading)
		{
			currentText.append(ch, start, length);
		}
		else if(bcost && startReading)
		{
			currentText.append(ch, start, length);
		}
	}
	public long timeStampConverter(int month, int day, int year, int hour)
	{
		//Time in seconds since January 1, 1970 in seconds
		Calendar calendar1 = Calendar.getInstance();//Accounts for leap year, but not daylight savings
		Calendar calendar2 = Calendar.getInstance();
		calendar1.set(1970, 0, 1, 0, 0);//year,month,day,hour,minute//January 1, 1970
		calendar2.set(year, month - 1, day, hour, 0);
		long milsecs1 = calendar1.getTimeInMillis();
		long milsecs2 = calendar2.getTimeInMillis();
		long diff = milsecs2 - milsecs1;
		long dhours = diff / (60 * 60 * 1000);	
		return ((dhours + 8) * 3600);//+8 accounts for the difference in time zone from GMT
	}
}

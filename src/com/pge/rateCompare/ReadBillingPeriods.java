package com.pge.rateCompare;
import java.io.File;
import java.util.*;
public class ReadBillingPeriods 
{

	public static ArrayList<String[]> readBillPeriods(String filename)
	{
		Scanner inFile = new Scanner("");
		Scanner inLine = new Scanner("");
		
		try
		{
			inFile = new Scanner(new File(filename));
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			System.out.println("Can't find Billing Periods File");
		}
		inFile.nextLine();//Name
		inFile.nextLine();//Address
		inFile.nextLine();//Account Number
		inFile.nextLine();//Service
		inFile.nextLine();//Blank Line
		inFile.nextLine();//Header
		
		ArrayList<String[]> listOfDates = new ArrayList<String[]>();
		while(inFile.hasNextLine())
		{
			inLine = new Scanner(inFile.nextLine());
			inLine.useDelimiter(",");
			inLine.next();//Type: Electric Billing
			String startDate = inLine.next();
			String endDate = inLine.next();
			Scanner parseDate = new Scanner(startDate);
			parseDate.useDelimiter("-");
			int startYear = Integer.valueOf(parseDate.next());
			int startMonth = Integer.valueOf(parseDate.next());
			int startDay = Integer.valueOf(parseDate.next());
			
			parseDate = new Scanner(endDate);
			parseDate.useDelimiter("-");
			int endYear = Integer.valueOf(parseDate.next());
			int endMonth = Integer.valueOf(parseDate.next());
			int endDay = Integer.valueOf(parseDate.next());
			String fullStartDate = startMonth + "-" + startDay + "-" + startYear;
			String fullEndDate = endMonth + "-" + endDay + "-" + endYear;
			String[] startEnd = new String [2];
			startEnd[0] = fullStartDate;
			startEnd[1] = fullEndDate;
			listOfDates.add(startEnd);
			parseDate.close();
		}
		
		inFile.close();
		inLine.close();
		return listOfDates;
		
	}
}

package com.pge.ev;

import java.util.*;


public class Tester 
{
	public final static double AVERAGE_EFFICIENCY = 3.2;

	public static double[] evBill(String fullStartDate, String fullEndDate, double chargingLevel, double weekdayDistance, double weekendDistance, int chargeStartTime, String dataFile)//called by the Rate comparer
	{		
		Scanner parseDate = new Scanner(fullStartDate);
		parseDate.useDelimiter("-");
		int startMonth = parseDate.nextInt();
		int startDay = parseDate.nextInt();
		int startYear = parseDate.nextInt();
		if(parseDate.hasNext())
		{	
			parseDate.next();//remove anything still in there
		}
		
		Scanner parseEndDate = new Scanner(fullEndDate);
		parseEndDate.useDelimiter("-");
		int endMonth = parseEndDate.nextInt();
		int endDay = parseEndDate.nextInt();
		int endYear = parseEndDate.nextInt();
		
		parseDate.close();
		parseEndDate.close();
		
		double[] usageAmounts = com.pge.ev.XMLReader.findPeakPartNonUsage(dataFile, startMonth, startDay, startYear, endMonth, endDay, endYear, chargeStartTime, weekdayDistance, weekendDistance, chargingLevel );
		
		double[] evTotalOffPartPeakSummer = com.pge.ev.PrintBill.calcSummerBill(usageAmounts);
		double[] evTotalOffPartPeakWinter = com.pge.ev.PrintBill.calcWinterBill(usageAmounts);
		double evBillParts[] = {evTotalOffPartPeakSummer[0] + evTotalOffPartPeakWinter[0], evTotalOffPartPeakSummer[1] + evTotalOffPartPeakWinter[1], evTotalOffPartPeakSummer[2] + evTotalOffPartPeakWinter[2], evTotalOffPartPeakSummer[3] + evTotalOffPartPeakWinter[3]};
		return evBillParts;
	}
	
}
	
	
	
	
	
	
	
	


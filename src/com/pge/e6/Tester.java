package com.pge.e6;

import java.util.*;

import com.pge.e1.BaselineQuantity;
import com.pge.e1.FileReader;
public class Tester 
{
	public static double[] e6Bill(String baseLineTerritory, String fullStartDate, String fullEndDate, String heatSource, double chargingLevel, double weekdayDistance, double weekendDistance, int chargeStartTime, String dataFile)
	{
		com.pge.e6.FileReader.readBaselineUsageFile();
		boolean isGas = false;
		if(heatSource.toLowerCase().equals("gas"))
		{
			isGas = true;
		}
		
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
	
		long[] numSummerWinterDays = BaselineQuantity.calcNumDays(startMonth, startDay, startYear, endMonth, endDay, endYear);
		boolean isSummer = true;
		
		double summerRate = FileReader.getBaselineAmount(baseLineTerritory, isSummer, isGas);//summerRate is the baseline Amount per day under specified constraints
		double summerDaysBaseline = numSummerWinterDays[0] * summerRate;
		
		isSummer = false;
		double winterRate = FileReader.getBaselineAmount(baseLineTerritory, isSummer, isGas);//baseline Amount per day
		double winterDaysBaseline = numSummerWinterDays[1] * winterRate;
		
		parseDate.close();
		parseEndDate.close();
		
		
		double[] usageAmounts = com.pge.e6.XMLReader.findPeakPartNonUsage(dataFile, startMonth, startDay, startYear, endMonth, endDay, endYear,  chargingLevel, weekdayDistance, weekendDistance, chargeStartTime);
		
		//CalcBill returns array with {total,t1,t2,t3,t4,offBill,partPeakBill,peakBill}
		double e6BillPartsSummer[] = com.pge.e6.PrintBill.calcBill(usageAmounts, summerDaysBaseline, numSummerWinterDays[0], true);
		double e6BillPartsWinter[] = com.pge.e6.PrintBill.calcBill(usageAmounts, winterDaysBaseline, numSummerWinterDays[1], false);
		double e6BillParts[] = {e6BillPartsSummer[0] + e6BillPartsWinter[0],e6BillPartsSummer[1] + e6BillPartsWinter[1],e6BillPartsSummer[2] + e6BillPartsWinter[2],e6BillPartsSummer[3] + e6BillPartsWinter[3],e6BillPartsSummer[4] + e6BillPartsWinter[4],e6BillPartsSummer[5] + e6BillPartsWinter[5],e6BillPartsSummer[6] + e6BillPartsWinter[6],e6BillPartsSummer[7] + e6BillPartsWinter[7]};
		return e6BillParts;
	}
}

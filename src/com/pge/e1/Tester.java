package com.pge.e1;
import java.util.Scanner;

import com.pge.rateCompare.NotInBillPeriodException;
public class Tester 
{
	public static double[] e1Bill(String baseLineTerritory, String fullStartDate, String fullEndDate, String heatSource, double chargingLevel, double weekdayDistance, double weekendDistance, int chargeStartTime, String dataFile) throws NotInBillPeriodException
	{
		com.pge.e1.FileReader.readBaselineUsageFile();
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
		
		
		double[] usageAmounts = com.pge.e1.XMLReader.findUsage(dataFile, startMonth, startDay, startYear, endMonth, endDay, endYear, chargingLevel, weekdayDistance, weekendDistance, chargeStartTime);
		
		double e1BillPartsSummer[] = com.pge.e1.PrintBill.calcBill(usageAmounts, summerDaysBaseline, numSummerWinterDays[0], true);
		double e1BillPartsWinter[] = com.pge.e1.PrintBill.calcBill(usageAmounts, winterDaysBaseline, numSummerWinterDays[1], false);
		double e1BillParts[] = {e1BillPartsSummer[0] + e1BillPartsWinter[0],e1BillPartsSummer[1] + e1BillPartsWinter[1],e1BillPartsSummer[2] + e1BillPartsWinter[2],e1BillPartsSummer[3] + e1BillPartsWinter[3],e1BillPartsSummer[4] + e1BillPartsWinter[4]};
		return e1BillParts;
	}
}

package com.pge.ev;
public class PrintBill
{

	final static double minimumRate = 0.14784;
	final static double meterRate = 0.25298;
	
	
	public static double calcBill(double[] usageParts)//Doesn't account for minimum charge
	{
		double[] tieredRates = FileReader.readPeaksTieredRateFile();
		double SPRate = tieredRates[0];
		double SPPRate = tieredRates[1];
		double SNPRate = tieredRates[2];
		double WPRate = tieredRates[3];
		double WPPRate = tieredRates[4];
		double WNPRate = tieredRates[5];
		
		double tempBill = 0;
		tempBill += SPRate * usageParts[0];
		tempBill += SPPRate * usageParts[1];
		tempBill += SNPRate * usageParts[2];
		tempBill += WPRate * usageParts[3];
		tempBill += WPPRate * usageParts[4];
		tempBill += WNPRate * usageParts[5];
		
		return tempBill; 
		
	}
	public static double[] calcSummerBill(double[] usageParts)//Doesn't account for minimum charge
	{
		double[] tieredRates = FileReader.readPeaksTieredRateFile();
		double SPRate = tieredRates[0];
		double SPPRate = tieredRates[1];
		double SNPRate = tieredRates[2];
		
		double peakBill = SPRate * usageParts[0];
		double partPeakBill = SPPRate * usageParts[1];
		double offPeakBill = SNPRate * usageParts[2];
		
		double totalSummerBill = peakBill + partPeakBill + offPeakBill;
		double[] returnArray = {totalSummerBill,offPeakBill, partPeakBill, peakBill};
		return returnArray; 
	}
	public static double[] calcWinterBill(double[] usageParts)//Doesn't account for minimum charge
	{
		double[] tieredRates = FileReader.readPeaksTieredRateFile();
		double WPRate = tieredRates[3];
		double WPPRate = tieredRates[4];
		double WNPRate = tieredRates[5];
		
		double peakBill = WPRate * usageParts[3];
		double partPeakBill = WPPRate * usageParts[4];
		double offPeakBill = WNPRate * usageParts[5];
		
		double totalWinterBill = peakBill + partPeakBill + offPeakBill;
		double[] returnArray = {totalWinterBill, offPeakBill, partPeakBill, peakBill};
		return returnArray; 
	}
	
}

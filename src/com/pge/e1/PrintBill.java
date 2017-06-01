package com.pge.e1;
import java.text.*;
public class PrintBill
{

	/*static double t1Rate = .16352;//these are the rates at the moment 7/1/2015
	static double t2Rate = .18673;
	static double t3Rate = .27504;
	static double t4Rate = .33504;*/
	final static double minimumRate = 0.14784;
	
	public static double[] calcBill(double[] usageParts, double baseline, long totalNumDays, boolean isSummer)//Doesn't account for minimum charge
	{
		double[] tieredRates = FileReader.readTieredRateFile();
		double t1Rate = tieredRates[0];
		double t2Rate = tieredRates[1];
		double t3Rate = tieredRates[2];
		double t4Rate = tieredRates[3];
		
		double summerUsage = Math.floor(usageParts[0]);
		double winterUsage = Math.floor(usageParts[1]);
		if(isSummer)
		{
			winterUsage = 0;
		}
		else
		{
			summerUsage = 0;
		}
		double usage = winterUsage + summerUsage;
		
		double t1Bill = 0, t2Bill = 0, t3Bill = 0, t4Bill = 0;//The contribution from each tier
		if(usage < baseline)
			t1Bill = usage * t1Rate;
		else
		{
			t1Bill = baseline * t1Rate;
			if(usage <= baseline * 1.3)
			{
				t2Bill = (usage - baseline) * t2Rate;
			}
			if(usage > baseline * 1.3)
			{
				t2Bill = (baseline * 1.3 - baseline) * t2Rate;
				if(usage <= baseline * 2)
				{
					t3Bill = (usage - (baseline * 1.3)) * t3Rate;
				}
				else
				{
					t3Bill = (baseline * 2 - baseline * 1.3) * t3Rate;
				}
			}		
			if(usage > baseline * 2)
				t4Bill = (usage - (baseline * 2)) * t4Rate;			
		}
		double totalBill = t1Bill + t2Bill + t3Bill + t4Bill;
		double tiers[] = new double[]{totalBill, t1Bill, t2Bill, t3Bill, t4Bill};
		return tiers; //return the contribution from each tier
		
	}
	
	
}

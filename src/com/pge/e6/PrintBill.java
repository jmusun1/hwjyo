package com.pge.e6;
public class PrintBill
{

	final static double minimumRate = 0.14784;
	final static double meterRate = 0.25298;
	
	
	public static double[] calcBill(double[] usageParts, double baseline, long totalNumDays, boolean isSummer)//Doesn't account for minimum charge
	{
		double[][] tieredRates = FileReader.readPeaksTieredRateFile();
		double t1SPRate = tieredRates[0][0];
		double t1SPPRate = tieredRates[0][1];
		double t1SNPRate = tieredRates[0][2];
		double t1WPPRate = tieredRates[0][3];
		double t1WNPRate = tieredRates[0][4];
		double t2SPRate = tieredRates[1][0]; 
		double t2SPPRate =  tieredRates[1][1];
		double t2SNPRate = tieredRates[1][2];
		double t2WPPRate = tieredRates[1][3];
		double t2WNPRate = tieredRates[1][4];
		double t3SPRate = tieredRates[2][0];
		double t3SPPRate = tieredRates[2][1];
		double t3SNPRate = tieredRates[2][2];
		double t3WPPRate = tieredRates[2][3];
		double t3WNPRate = tieredRates[2][4];
		double t4SPRate = tieredRates[3][0];
		double t4SPPRate = tieredRates[3][1];
		double t4SNPRate = tieredRates[3][2];
		double t4WPPRate = tieredRates[3][3];
		double t4WNPRate = tieredRates[3][4];
		//There is a tier 5 but it is the same as tier 4
	
		double summerPeak = usageParts[0];
		double summerPartPeak = usageParts[1];
		double summerNonPeak = usageParts[2];
		double winterPartPeak = usageParts[3];
		double winterNonPeak = usageParts[4];
		double summerPeakPercentage = 0, summerPartPeakPercentage = 0, summerNonPeakPercentage = 0, winterPartPeakPercentage = 0, winterNonPeakPercentage = 0;
		if(isSummer)
		{
			if(summerPeak != 0) summerPeakPercentage = summerPeak /(summerPeak + summerPartPeak + summerNonPeak);
			if(summerPartPeak != 0) summerPartPeakPercentage = summerPartPeak /(summerPeak + summerPartPeak + summerNonPeak);
			if(summerNonPeak != 0) summerNonPeakPercentage = summerNonPeak /(summerPeak + summerPartPeak + summerNonPeak);

			winterPartPeak = 0;
			winterNonPeak = 0;
		}
		else
		{
			if(winterPartPeak != 0) winterPartPeakPercentage = winterPartPeak /(winterPartPeak + winterNonPeak);
			if(winterNonPeak != 0)winterNonPeakPercentage = winterNonPeak /(winterPartPeak + winterNonPeak);
	
			summerPeak = 0;
			summerPartPeak = 0;
			summerNonPeak = 0;
		}
		double totalUsage = summerPeak + summerPartPeak + summerNonPeak + winterPartPeak + winterNonPeak;
		
		double t1Bill = 0, t2Bill = 0, t3Bill = 0, t4Bill = 0;//The contribution from each tier
		if(totalUsage < baseline)
		{
			t1Bill += t1SPRate * totalUsage * summerPeakPercentage;
			t1Bill += t1SPPRate * totalUsage * summerPartPeakPercentage;
			t1Bill += t1SNPRate * totalUsage * summerNonPeakPercentage;
			t1Bill += t1WPPRate * totalUsage * winterPartPeakPercentage;
			t1Bill += t1WNPRate * totalUsage * winterNonPeakPercentage;
		}
			
		else
		{
			t1Bill += t1SPRate * baseline * summerPeakPercentage;
			t1Bill += t1SPPRate * baseline * summerPartPeakPercentage;
			t1Bill += t1SNPRate * baseline * summerNonPeakPercentage;
			t1Bill += t1WPPRate * baseline * winterPartPeakPercentage;
			t1Bill += t1WNPRate * baseline * winterNonPeakPercentage;
			if(totalUsage <= baseline * 1.3)
			{
				t2Bill += t2SPRate * (totalUsage - baseline) * summerPeakPercentage;
				t2Bill += t2SPPRate * (totalUsage - baseline) * summerPartPeakPercentage;
				t2Bill += t2SNPRate * (totalUsage - baseline) * summerNonPeakPercentage;
				t2Bill += t2WPPRate * (totalUsage - baseline) * winterPartPeakPercentage;
				t2Bill += t2WNPRate * (totalUsage - baseline) * winterNonPeakPercentage;
			}
			if(totalUsage > baseline * 1.3)
			{
				t2Bill += t2SPRate * (baseline * 1.3 - baseline) * summerPeakPercentage;
				t2Bill += t2SPPRate * (baseline * 1.3 - baseline) * summerPartPeakPercentage;
				t2Bill += t2SNPRate * (baseline * 1.3 - baseline) * summerNonPeakPercentage;
				t2Bill += t2WPPRate * (baseline * 1.3 - baseline) * winterPartPeakPercentage;
				t2Bill += t2WNPRate * (baseline * 1.3 - baseline) * winterNonPeakPercentage;
				if(totalUsage <= baseline * 2)
				{
					t3Bill += t3SPRate * (totalUsage - (baseline * 1.3)) * summerPeakPercentage;
					t3Bill += t3SPPRate * (totalUsage - (baseline * 1.3)) * summerPartPeakPercentage;
					t3Bill += t3SNPRate * (totalUsage - (baseline * 1.3)) * summerNonPeakPercentage;
					t3Bill += t3WPPRate * (totalUsage - (baseline * 1.3)) * winterPartPeakPercentage;
					t3Bill += t3WNPRate * (totalUsage - (baseline * 1.3)) * winterNonPeakPercentage;
				}
				else//goes into tier 4
				{
					t3Bill += t3SPRate * (baseline * 2 - baseline * 1.3) * summerPeakPercentage;
					t3Bill += t3SPPRate * (baseline * 2 - baseline * 1.3) * summerPartPeakPercentage;
					t3Bill += t3SNPRate * (baseline * 2 - baseline * 1.3) * summerNonPeakPercentage;
					t3Bill += t3WPPRate * (baseline * 2 - baseline * 1.3) * winterPartPeakPercentage;
					t3Bill += t3WNPRate * (baseline * 2 - baseline * 1.3) * winterNonPeakPercentage;
				}
			}		
			if(totalUsage > (baseline * 2))
			{
				t4Bill += t4SPRate * (totalUsage - (baseline * 2)) * summerPeakPercentage;
				t4Bill += t4SPPRate * (totalUsage - (baseline * 2)) * summerPartPeakPercentage;
				t4Bill += t4SNPRate * (totalUsage - (baseline * 2)) * summerNonPeakPercentage;
				t4Bill += t4WPPRate * (totalUsage - (baseline * 2)) * winterPartPeakPercentage;
				t4Bill += t4WNPRate * (totalUsage - (baseline * 2)) * winterNonPeakPercentage;
			}
							
		}
		//meter charge not included
		double totalBill =  t1Bill + t2Bill + t3Bill + t4Bill;
		
		double peakBill = (totalBill * summerPeakPercentage);
		double partPeakBill = (totalBill * summerPartPeakPercentage) + (totalBill * winterPartPeakPercentage);
		double nonPeakBill = (totalBill * summerNonPeakPercentage) + (totalBill * winterNonPeakPercentage);;
		
		double tiers[] = new double[]{totalBill, t1Bill, t2Bill, t3Bill, t4Bill, nonPeakBill, partPeakBill, peakBill};
		return tiers; //return the contribution from each tier
		
	}
	
	
}

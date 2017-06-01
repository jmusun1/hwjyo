package com.pge.rateCompare;
import java.text.DecimalFormat;
import java.util.*;
public class Compare
{
	
	public static double[] compare(String usageFilename, String billingFilename, String baselineTerritory, String heatSource, double chargingLevel, double weekdayDistance, double weekendDistance, int chargeStartTime)
	{
		ArrayList<String[]> billingDates = com.pge.rateCompare.ReadBillingPeriods.readBillPeriods(billingFilename);
		double e1Total = 0, e1T1 = 0, e1T2 = 0, e1T3 = 0, e1T4 = 0;
		double e6Total = 0, e6T1 = 0, e6T2 = 0, e6T3 = 0, e6T4 = 0, e6Off = 0, e6Part = 0, e6Peak = 0;
		double evTotal = 0, evOff = 0, evPart = 0, evPeak = 0;
		double[] e1Parts = {};
		double[] e6Parts = {};
		double[] evParts = {};
		int numMonths = 0;
		
		for(String[] date: billingDates)
		{
			try
			{
				e1Parts = com.pge.e1.Tester.e1Bill(baselineTerritory, date[0], date[1], heatSource, chargingLevel, weekdayDistance, weekendDistance, chargeStartTime, usageFilename);
				e6Parts = com.pge.e6.Tester.e6Bill(baselineTerritory, date[0], date[1], heatSource, chargingLevel, weekdayDistance, weekendDistance, chargeStartTime, usageFilename);
				evParts = com.pge.ev.Tester.evBill(date[0], date[1], chargingLevel, weekdayDistance, weekendDistance, chargeStartTime, usageFilename);
				numMonths++;
			}
			catch(NotInBillPeriodException e)//If the billing date isn't in the billing period then an exception is thrown to skip that month
			{
				continue;
				//Do nothing
			}
			System.out.println("date: " + date[0]);
			System.out.println("E1Total: " + e1Parts[0]);
			System.out.println("E6Total: " + e6Parts[0]);
			System.out.println("EVTotal: " + evParts[0]);
			System.out.println();
			e1Total += e1Parts[0];
			e1T1 += e1Parts[1];
			e1T2 += e1Parts[2];
			e1T3 += e1Parts[3];
			e1T4 += e1Parts[4];
			e6Total += e6Parts[0];
			e6T1 += e6Parts[1];
			e6T2 += e6Parts[2];
			e6T3 += e6Parts[3];
			e6T4 += e6Parts[4];
			e6Off += e6Parts[5];
			e6Part += e6Parts[6];
			e6Peak += e6Parts[7];
			evTotal += evParts[0];
			evOff += evParts[1];
			evPart += evParts[2];
			evPeak += evParts[3];
		}
		e1Total /= numMonths;
		e1T1 /= numMonths;
		e1T2 /= numMonths;
		e1T3 /= numMonths;
		e1T4 /= numMonths;
		e6Total /= numMonths;
		e6T1 /= numMonths;
		e6T2 /= numMonths;
		e6T3 /= numMonths;
		e6T4 /= numMonths;
		e6Off /= numMonths;
		e6Part /= numMonths;
		e6Peak /= numMonths;
		evTotal /= numMonths;
		evOff /= numMonths;
		evPart /= numMonths;
		evPeak /= numMonths;
		
		e1Total = Math.round(e1Total * 100) / 100.0;
		e1T1 =  Math.round(e1T1 * 100) / 100.0;
		e1T2 =  Math.round(e1T2 * 100) / 100.0;
		e1T3 =  Math.round(e1T3 * 100) / 100.0;
		e1T4 = Math.round(e1T4 * 100) / 100.0;
		e6Total = Math.round(e6Total * 100) / 100.0;
		e6T1 = Math.round(e6T1 * 100) / 100.0;
		e6T2 =  Math.round(e6T2 * 100) / 100.0;
		e6T3 = Math.round(e6T3 * 100) / 100.0;
		e6T4 = Math.round(e6T4 * 100) / 100.0;
		e6Off = Math.round(e6Off * 100) / 100.0;
		e6Part =  Math.round(e6Part * 100) / 100.0;
		e6Peak = Math.round(e6Peak * 100) / 100.0;
		evTotal = Math.round(evTotal * 100) / 100.0;
		evOff = Math.round(evOff * 100) / 100.0;
		evPart = Math.round(evPart * 100) / 100.0;
		evPeak = Math.round(evPeak * 100) / 100.0;
		DecimalFormat df = new DecimalFormat("####0.00");
		System.out.println("Average E1 Bill: $" + df.format(e1Total));
		System.out.println("Average E6 Bill: $" + df.format(e6Total));
		System.out.println("Average EV Bill: $" + df.format(evTotal));
		double returnArray[] = {e1Total , e1T1 , e1T2 , e1T3 , e1T4, e6Total , e6T1 , e6T2 , e6T3 , e6T4 , e6Off , e6Part , e6Peak, evTotal , evOff , evPart , evPeak};
		return returnArray;
	}
}

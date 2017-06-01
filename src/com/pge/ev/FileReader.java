package com.pge.ev;

import java.util.*;



import java.io.*;
public class FileReader 
{
	public static double[] readPeaksTieredRateFile() 
	{
		
		Scanner inFile = new Scanner("");
		String lineData = "";
		Scanner inLine = new Scanner("");
		try
		{
			inFile = new Scanner(new File(FileReader.class.getClassLoader().getResource("RateFiles/EV_Rate_Components_Peaks.txt").getPath()));
			lineData = inFile.nextLine();//Gets rid of header
			inLine = new Scanner(lineData);
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			System.out.println("Can't find tieredRateFile");
		}
		
		double tempRate = 0;
		double[] returnArray = new double[6];
		for(int i = 0; i < 6; i++)
		{
			inFile.nextLine();
			inLine = new Scanner(inFile.nextLine());
			while(inLine.hasNext())
			{
				tempRate += inLine.nextDouble();
			}
			returnArray[i] = tempRate;
			tempRate = 0;
		}
		inFile.close();
		inLine.close();
		
		return returnArray;
	}	
	
	
	public static double[][] getBreakdownRates()
	{
		Scanner inFile = new Scanner("");
		String lineData = "";
		Scanner inLine = new Scanner("");
		try
		{
			inFile = new Scanner(new File(FileReader.class.getClassLoader().getResource("RateFiles/EV_Rate_Components_Peaks.txt").getPath()));
			lineData = inFile.nextLine();//gets rid of header
			inLine = new Scanner(lineData);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Can't find tieredRateFile");
		}
		double[][] returnArray = new double[13][6];//Component-Peak
		for(int j = 0; j < 6; j++)
		{
			inFile.nextLine();//deletes the line that says the type of season and peak
			inLine = new Scanner(inFile.nextLine());
			for(int i = 0; i < 13; i++)
			{
				returnArray[i][j] = inLine.nextDouble();
			}
		}
		
		inLine.close();
		inFile.close();
		return returnArray;
	}
	
	
	public static String[][] readWinterPeakTimes()
	{
		Scanner inFile = new Scanner("");
		String lineData = "";
		Scanner inLine = new Scanner("");
		inLine.useDelimiter(",");
		try
		{
			inFile = new Scanner(new File(FileReader.class.getClassLoader().getResource("RateFiles/EV_Winter_Peak_Times.csv").getPath()));
			lineData = inFile.nextLine();//gets rid of first line with the list of times
			inLine = new Scanner(lineData);
			inLine.useDelimiter(",");
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			System.out.println("Can't find Winter Peak Times File");
		}
		String[][] returnArray = new String[7][24];
		for(int i = 0; i < 7; i++)
		{
			inLine = new Scanner(inFile.nextLine());
			inLine.useDelimiter(",");
			inLine.next();//gets rid of Day
			for(int j = 0; j < 24; j++)
			{
				returnArray[i][j] = inLine.next();
			}
		}
		inLine.close();
		inFile.close();
		return returnArray;
	}
	public static String[][] readSummerPeakTimes()
	{
		Scanner inFile = new Scanner("");
		String lineData = "";
		Scanner inLine = new Scanner("");
		inLine.useDelimiter(",");
		try
		{
			inFile = new Scanner(new File(FileReader.class.getClassLoader().getResource("RateFiles/EV_Summer_Peak_Times.csv").getPath()));
			lineData = inFile.nextLine();//gets rid of first line with the list of times
			inLine = new Scanner(lineData);
			inLine.useDelimiter(",");
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			System.out.println("Can't find Summer Peak Times File");
		}
		String[][] returnArray = new String[7][24];
		for(int i = 0; i < 7; i++)
		{
			inLine = new Scanner(inFile.nextLine());
			inLine.useDelimiter(",");
			inLine.next();//gets rid of Day
			for(int j = 0; j < 24; j++)
			{
				returnArray[i][j] = inLine.next();
			}
		}
		inLine.close();
		inFile.close();
		return returnArray;
	}
	public static String[][] readSummerPeakTimesDST()//Daylight savings time
	{
		Scanner inFile = new Scanner("");
		String lineData = "";
		Scanner inLine = new Scanner("");
		inLine.useDelimiter(",");
		try
		{
			inFile = new Scanner(new File(FileReader.class.getClassLoader().getResource("RateFiles/EV_Summer_Peak_Times_DaylightSavings.csv").getPath()));
			lineData = inFile.nextLine();//gets rid of first line with the list of times
			inLine = new Scanner(lineData);
			inLine.useDelimiter(",");
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			System.out.println("Can't find Summer Peak Daylight Savings Time File");
		}
		String[][] returnArray = new String[7][24];
		for(int i = 0; i < 7; i++)
		{
			inLine = new Scanner(inFile.nextLine());
			inLine.useDelimiter(",");
			inLine.next();//gets rid of Day
			for(int j = 0; j < 24; j++)
			{
				returnArray[i][j] = inLine.next();
			}
		}
		inLine.close();
		inFile.close();
		return returnArray;
	}
	
	public static String[][] readWinterPeakTimesDST()
	{
		Scanner inFile = new Scanner("");
		String lineData = "";
		Scanner inLine = new Scanner("");
		inLine.useDelimiter(",");
		try
		{
			inFile = new Scanner(new File(FileReader.class.getClassLoader().getResource("RateFiles/EV_Winter_Peak_Times_DaylightSavings.csv").getPath()));
			lineData = inFile.nextLine();//gets rid of first line with the list of times
			inLine = new Scanner(lineData);
			inLine.useDelimiter(",");
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			System.out.println("Can't find Winter Peak Daylight Savings Times File");
		}
		String[][] returnArray = new String[7][24];
		for(int i = 0; i < 7; i++)
		{
			inLine = new Scanner(inFile.nextLine());
			inLine.useDelimiter(",");
			inLine.next();//gets rid of Day
			for(int j = 0; j < 24; j++)
			{
				returnArray[i][j] = inLine.next();
			}
		}
		inLine.close();
		inFile.close();
		return returnArray;
	}
	
	public static List<String> readHolidaysFile()
	{
		Scanner inFile = new Scanner("");
		String lineData = "";
		Scanner inLine = new Scanner("");
		List<String> list = new ArrayList<>();
		try
		{
			inFile = new Scanner(new File(FileReader.class.getClassLoader().getResource("RateFiles/Holidays.txt").getPath()));
			lineData = inFile.nextLine();//gets rid of first line 
			inLine = new Scanner(lineData);
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			System.out.println("Can't find Holidays File");
		}
		
		while(inFile.hasNextLine())
		{
			list.add(inFile.nextLine());
		}
		inFile.close();
		inLine.close();
		return list;
	}
}

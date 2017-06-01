package com.pge.e6;

import java.util.*;



import java.io.*;
public class FileReader 
{
	
	public static void readBaselineUsageFile() 
	{
		Scanner inFile = new Scanner("");
		String lineData = "";
		Scanner inLine = new Scanner("");
		try
		{
			inFile = new Scanner(new File(com.pge.e6.FileReader.class.getClassLoader().getResource("RateFiles/BaseLineQuantity_Territory_HeatType_Season.txt").getPath()));
			lineData = inFile.nextLine();//Gets rid of header
			inLine = new Scanner(lineData);
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			System.out.println("Can't find BaseLineQuantity_Territory_HeatType_Season File");
		}
		
		
		int lineNum = 2; //First Line was the header information

		BaselineTerritory territoryP = new BaselineTerritory();
		BaselineTerritory territoryQ = new BaselineTerritory();
		BaselineTerritory territoryR = new BaselineTerritory();
		BaselineTerritory territoryS = new BaselineTerritory();
		BaselineTerritory territoryT = new BaselineTerritory();
		BaselineTerritory territoryV = new BaselineTerritory();
		BaselineTerritory territoryW = new BaselineTerritory();
		BaselineTerritory territoryX = new BaselineTerritory();
		BaselineTerritory territoryY = new BaselineTerritory();
		BaselineTerritory territoryZ = new BaselineTerritory();
		
		while(inFile.hasNextLine())
		{
			if(lineNum == 2)//Territory P
			{
				inLine = new Scanner(inFile.nextLine());
				inLine.next();//Should be the territory letter
				territoryP = new BaselineTerritory(inLine.nextDouble(),inLine.nextDouble(),inLine.nextDouble(),inLine.nextDouble());
			}
			else if(lineNum == 3)//Territory Q
			{
				inLine = new Scanner(inFile.nextLine());
				inLine.next();//Should be the territory letter
				territoryQ = new BaselineTerritory(inLine.nextDouble(),inLine.nextDouble(),inLine.nextDouble(),inLine.nextDouble());
			}
			else if(lineNum == 4)//Territory R
			{
				inLine = new Scanner(inFile.nextLine());
				inLine.next();//Should be the territory letter
				territoryR = new BaselineTerritory(inLine.nextDouble(),inLine.nextDouble(),inLine.nextDouble(),inLine.nextDouble());
			}
			else if(lineNum == 5)//Territory S
			{
				inLine = new Scanner(inFile.nextLine());
				inLine.next();//Should be the territory letter
				territoryS = new BaselineTerritory(inLine.nextDouble(),inLine.nextDouble(),inLine.nextDouble(),inLine.nextDouble());
			}
			else if(lineNum == 6)//Territory T
			{
				inLine = new Scanner(inFile.nextLine());
				inLine.next();//Should be the territory letter
				territoryT = new BaselineTerritory(inLine.nextDouble(),inLine.nextDouble(),inLine.nextDouble(),inLine.nextDouble());
			}
			else if(lineNum == 7)//Territory V
			{
				inLine = new Scanner(inFile.nextLine());
				inLine.next();//Should be the territory letter
				territoryV = new BaselineTerritory(inLine.nextDouble(),inLine.nextDouble(),inLine.nextDouble(),inLine.nextDouble());
			}
			else if(lineNum == 8)//Territory W
			{
				inLine = new Scanner(inFile.nextLine());
				inLine.next();//Should be the territory letter
				territoryW = new BaselineTerritory(inLine.nextDouble(),inLine.nextDouble(),inLine.nextDouble(),inLine.nextDouble());
			}
			else if(lineNum == 9)//Territory X
			{
				inLine = new Scanner(inFile.nextLine());
				inLine.next();//Should be the territory letter
				territoryX = new BaselineTerritory(inLine.nextDouble(),inLine.nextDouble(),inLine.nextDouble(),inLine.nextDouble());
			}
			else if(lineNum == 10)//Territory Y
			{
				inLine = new Scanner(inFile.nextLine());
				inLine.next();//Should be the territory letter
				territoryY = new BaselineTerritory(inLine.nextDouble(),inLine.nextDouble(),inLine.nextDouble(),inLine.nextDouble());
			}
			else if(lineNum == 11)//Territory Z
			{
				inLine = new Scanner(inFile.nextLine());
				inLine.next();//Should be the territory letter
				territoryZ = new BaselineTerritory(inLine.nextDouble(),inLine.nextDouble(),inLine.nextDouble(),inLine.nextDouble());
			}
			else
			{
				inLine.next();
			}
			lineNum++;
		}
		inFile.close();
		inLine.close();

		Territories.territoryP = territoryP;
		Territories.territoryQ = territoryQ;
		Territories.territoryR = territoryR;
		Territories.territoryS = territoryS;
		Territories.territoryT = territoryT;
		Territories.territoryV = territoryV;
		Territories.territoryW = territoryW;
		Territories.territoryX = territoryX;
		Territories.territoryY = territoryY;
		Territories.territoryZ = territoryZ;
	}
	
	public static BaselineTerritory getTerritoryP(){return Territories.territoryP;}
	public static BaselineTerritory getTerritoryQ(){return Territories.territoryQ;}
	public static BaselineTerritory getTerritoryR(){return Territories.territoryR;}
	public static BaselineTerritory getTerritoryS(){return Territories.territoryS;}
	public static BaselineTerritory getTerritoryT(){return Territories.territoryT;}
	public static BaselineTerritory getTerritoryV(){return Territories.territoryV;}
	public static BaselineTerritory getTerritoryW(){return Territories.territoryW;}
	public static BaselineTerritory getTerritoryX(){return Territories.territoryX;}
	public static BaselineTerritory getTerritoryY(){return Territories.territoryY;}
	public static BaselineTerritory getTerritoryZ(){return Territories.territoryZ;}
	
	public static double getBaselineAmount(String terr, boolean isSummer, boolean isGas)
	{
		BaselineTerritory finalTerr = getTerritoryP();
		terr.toLowerCase();
		if (terr.toLowerCase().equals("p")) finalTerr = getTerritoryP();
		else if(terr.toLowerCase().equals("q")) finalTerr = getTerritoryQ();
		else if(terr.toLowerCase().equals("r")) finalTerr = getTerritoryR();
		else if(terr.toLowerCase().equals("s")) finalTerr = getTerritoryS();
		else if(terr.toLowerCase().equals("t")) finalTerr = getTerritoryT();
		else if(terr.toLowerCase().equals("v")) finalTerr = getTerritoryV();
		else if(terr.toLowerCase().equals("w")) finalTerr = getTerritoryW();
		else if(terr.toLowerCase().equals("x")) finalTerr = getTerritoryX();
		else if(terr.toLowerCase().equals("y")) finalTerr = getTerritoryY();
		else if(terr.toLowerCase().equals("z")) finalTerr = getTerritoryZ();
		else System.out.println("Not a valid territory");
		
		
		if(isSummer)
		{
			if(isGas)
			{
				return finalTerr.summerGas;
			}
			else
			{
				return finalTerr.summerElectric;
			}
		}
		else
		{
			if(isGas)
			{
				return finalTerr.winterGas;
			}
			else
			{
				return finalTerr.winterElectric;
			}
		}	
	}
	

	public static double[][] readPeaksTieredRateFile() 
	{
		
		Scanner inFile = new Scanner("");
		String lineData = "";
		Scanner inLine = new Scanner("");
		try
		{
			inFile = new Scanner(new File(FileReader.class.getClassLoader().getResource("RateFiles/E6_Rate_Components_Peaks.txt").getPath()));
			lineData = inFile.nextLine();//Gets rid of header
			inLine = new Scanner(lineData);
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			System.out.println("Can't find tieredRateFile");
		}
		
		
		
		double tempRate = 0;
		double[][] returnArray = new double[5][5];
		for(int i = 0; i < 5; i++)
		{
			if(inFile.hasNextLine())
			{
				inFile.nextLine();
				for(int j = 0; j < 5; j++)
				{
					inLine = new Scanner(inFile.nextLine());
					while(inLine.hasNext())
					{
						tempRate += inLine.nextDouble();
					}
					returnArray[j][i] = tempRate;
					tempRate = 0;
				}
			}
		}
		inFile.close();
		inLine.close();

		return returnArray;
	}	
	
	public static double[][][] getBreakdownRates()
	{
		Scanner inFile = new Scanner("");
		String lineData = "";
		Scanner inLine = new Scanner("");
		try
		{
			inFile = new Scanner(new File(FileReader.class.getClassLoader().getResource("RateFiles/E6_Rate_Components_Peaks.txt").getPath()));
			lineData = inFile.nextLine();//gets rid of header
			inLine = new Scanner(lineData);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Can't find tieredRateFile");
		}
		double[][][] returnArray = new double[13][5][5];//Component-Peak-Tiers
		for(int j = 0; j < 5; j++)
		{
			inFile.nextLine();//deletes the line that says the type of season and peak
			inLine = new Scanner(inFile.nextLine());
			for(int i = 0; i < 13; i++)
			{
				returnArray[i][j][0] = inLine.nextDouble();
			}
			inLine = new Scanner(inFile.nextLine());
			for(int i = 0; i < 13; i++)//summer peak tier 2
			{
				returnArray[i][j][1] = inLine.nextDouble();
			}
			inLine = new Scanner(inFile.nextLine());
			for(int i = 0; i < 13; i++)
			{
				returnArray[i][j][2] = inLine.nextDouble();
			}
			inLine = new Scanner(inFile.nextLine());
			for(int i = 0; i < 13; i++)
			{
				returnArray[i][j][3] = inLine.nextDouble();
			}
			inLine = new Scanner(inFile.nextLine());
			for(int i = 0; i < 13; i++)
			{
				returnArray[i][j][4] = inLine.nextDouble();
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
			inFile = new Scanner(new File(FileReader.class.getClassLoader().getResource("RateFiles/E6_Winter_Peak_Times.csv").getPath()));
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
			inFile = new Scanner(new File(FileReader.class.getClassLoader().getResource("RateFiles/E6_Summer_Peak_Times.csv").getPath()));
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
			inFile = new Scanner(new File(FileReader.class.getClassLoader().getResource("RateFiles/E6_Summer_Peak_Times_DaylightSavings.csv").getPath()));
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
			inFile = new Scanner(new File(FileReader.class.getClassLoader().getResource("RateFiles/E6_Winter_Peak_Times_DaylightSavings.csv").getPath()));
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

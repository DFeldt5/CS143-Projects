import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * A class representing weather data including temperatures, snowfall, and
 * precipitation over a period of time.
 * 
 * Feldt, Dustin
 * CS 143 Assignment 5
 * 
 */
public class WeatherData {
	//{year : total snowfall that year}
	private Map<Integer, Double> totalSnowfallPerYear = new HashMap<>();
	//{month : List of all high temperatures that month}
	private Map<Integer, List<Integer>> highTempsPerMonth = new HashMap<>();
	//{month : total precipitation for that month}
	private Map<Integer, Double> totalMonthlyPrecipitation = new HashMap<>();
	//set of all high temperatures in the data
	private Set<Integer> allHighTemps = new HashSet<Integer>();
	//set of all low temperatures in the data
	private Set<Integer> allLowTemps = new HashSet<Integer>();
	//{month : {high temperature : number of times that temperature appears}}
	private Map<Integer, Map<Integer, Integer>> highestTemps = new HashMap<>();
	//{month : lowest most-frequent high temperature that month}
	private Map<Integer, Integer> lowestCommonHigh = new HashMap<>();
	//{set of all low temperatures : highest high temperature recorded with that low}
	private Map<Integer, Integer> lowsAndHighs = new HashMap<>();
	//tracks a given high temperature
	private int high = 0;
	
	/**
	 * @param file Scanner connected to a weather data file
	 */
	public WeatherData(Scanner file) {
		// discard the header
		file.nextLine();
		// loop through each row of data
		while(file.hasNextLine()) {
			//split each row into strings and strip out quotes
			String[] row = file.nextLine().split(",");
			for(int i = 0; i < row.length; i++) {
				if(!row[i].isEmpty()) {
					row[i] = row[i].substring(1, row[i].length() - 1);
				}
			}
			//extract date
			String date = row[1];
			//split date into [y,m,d]
			String[] dateArray = date.split("-");
			int month = Integer.parseInt(dateArray[1]);
			int year = Integer.parseInt(dateArray[0]);
			
			//snowfall data
			if(row.length >= 6 && !row[5].isEmpty()) {
				double snowfall = Double.parseDouble(row[5]);
				//check snowfall map for the current month and update data
				if(totalSnowfallPerYear.containsKey(year)) {
					double prevSnowfall = totalSnowfallPerYear.get(year);
					double newTotal = prevSnowfall + snowfall;
					totalSnowfallPerYear.put(year, newTotal);
				}
				//create new mapping if none exists
				else {
					totalSnowfallPerYear.put(year, snowfall);
				}
			}
					
			//daily high temperature data
			if(row.length >= 8 && !row[7].isEmpty()) {
				// extract the high temp
				int highTemp = Integer.parseInt(row[7]);
				//store temperature for highestHighForLow
				high = highTemp;
				//add to allHighTemps set
				allHighTemps.add(highTemp);
				//add to list of monthly high temps, by updating existing or creating new maps
				if(highTempsPerMonth.containsKey(month)) {
					List<Integer> highTemps = highTempsPerMonth.get(month);
					highTemps.add(highTemp);
					highTempsPerMonth.replace(month, highTemps);
				}
				else {
					List<Integer> highTemps = new ArrayList<>();
					highTemps.add(highTemp);
					highTempsPerMonth.put(month, highTemps);
				}
			}
					
			//daily low temperature data
			if(row.length >= 9 && !row[8].isEmpty()) {
				// extract the low temp
				int lowTemp = Integer.parseInt(row[8]);
				//add to set of all low temperatures
				allLowTemps.add(lowTemp);
				//check existing lowsAndHighs map for this low and compare to high
				if(lowsAndHighs.containsKey(lowTemp)) {
					//update map if this high is higher
					if(lowsAndHighs.get(lowTemp) < high) {
						lowsAndHighs.replace(lowTemp, high);
					}
				}
				//add map if new low temp
				else {
					lowsAndHighs.put(lowTemp, high);
				}
			}
			
			//precipitation data
			if(!row[3].isEmpty() || !row[4].isEmpty()) {
				//counter for precipitation
				double precipitation = 0.0;
				//get either multi-day or single-day precipitation data
				if(!row[3].isEmpty()) {
					precipitation = Double.parseDouble(row[3]);
				}
				else {
					precipitation = Double.parseDouble(row[4]);
				}
				//update existing map total or create new mapping
				if(totalMonthlyPrecipitation.containsKey(month)) {
					double prevPrecip = totalMonthlyPrecipitation.get(month);
					double newTotal = prevPrecip + precipitation;
					totalMonthlyPrecipitation.put(month, newTotal);
				}
				else {
					totalMonthlyPrecipitation.put(month, precipitation);
				}
			}
			
		}
		
		//loop to generate lowest common high
		//iterate through monthly high temp map
		for(int month : highTempsPerMonth.keySet()) {
			//map to track how often each high appears {temp : count}
			Map<Integer, Integer> countPerTemp = new HashMap<>();
			//get the list of highs for that month
			List<Integer> highTemps = highTempsPerMonth.get(month);
			//iterate through list of high temps
			for(int temp : highTemps) {
				//if temp is stored, update count map
				if(countPerTemp.containsKey(temp)) {
					int count = countPerTemp.get(temp);
					count++;
					countPerTemp.replace(temp, count);
				}
				//if the month doesn't exist yet, create new mapping
				else {
					countPerTemp.put(temp, 1);
				}
				
			}
			//map this month to its corresponding map of high temp counts
			highestTemps.put(month, countPerTemp);
			//keep track of highest count seen so far
			int highest = 0;
			//save which temperature maps to that count
			int tempTracker = 0;
			//iterate through monthly high count map to match most common high
			Map<Integer, Integer> temps = highestTemps.get(month);
			for(int temp : temps.keySet()) {
				int frequency = temps.get(temp);
				if(frequency > highest) {
					highest = frequency;
					tempTracker = temp;
				}
				else {
					continue;
				}
			}
			//map month to desired temperature
			lowestCommonHigh.put(month, tempTracker);
		}

	}

	/**
	 * Determine whether the given temperature was ever seen as a high temperature
	 * in the data provided to the constructor.
	 */
	public boolean highTemp(int degrees) {
		return allHighTemps.contains(degrees);
	}

	/**
	 * Determine whether the given temperature was ever seen as a low temperature in
	 * the data provided to the constructor.
	 */
	public boolean lowTemp(int degrees) {
		return allLowTemps.contains(degrees);
	}

	/**
	 * Determine the total amount of snowfall recorded in the given year.
	 */
	public double totalSnowfallForYear(int year) {
		if(!totalSnowfallPerYear.containsKey(year)) {
			return 0.0;
		}
		return totalSnowfallPerYear.get(year);
	}

	/**
	 * Determine the average (mean) total precipitation recorded for the given
	 * month.
	 */
	public double averagePrecipitationForMonth(int month) {
		if(!totalMonthlyPrecipitation.containsKey(month)) {
			return 0.0;
		}
		
		return totalMonthlyPrecipitation.get(month)/100;
	}

	/**
	 * Return the most common (most often observed) high temperature seen in the
	 * given month. If there are two or more temperatures that are both seen the
	 * most number of times, return the lowest high temperature.
	 */
	public int lowestMostCommonHighForMonth(int month) {
		return lowestCommonHigh.get(month);
	}

	/**
	 * For the given low temperature, find the highest high temperature seen with
	 * that low.
	 */
	public int highestHighForLow(int degrees) {
		return lowsAndHighs.get(degrees);
	}
}

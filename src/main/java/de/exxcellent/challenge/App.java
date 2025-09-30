package de.exxcellent.challenge;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * The entry class for your solution. This class is only aimed as starting point and not intended as baseline for your software
 * design. Read: create your own classes and packages as appropriate.
 *
 * @author Benjamin Schmid <benjamin.schmid@exxcellent.de>
 */
public final class App {

    /**
     * This is the main entry method of your program.
     * @param args The CLI arguments passed
     */
    public static void main(String... args) {
   
     	String path1 = "de/exxcellent/challenge/weather.csv";
    	String col1 = "MxT";
    	String col2 = "MnT";
    	String path2 = "de/exxcellent/challenge/football.csv";
    	String col3 = "Goals";
    	String col4 = "Goals Allowed";
    	
        TableReader reader1 = new CSVReader(path1);
        TableReader reader2 = new CSVReader(path2);
        Table tab1 = null;
        Table tab2 = null;
        try {
			tab1 = reader1.readAsTable();
			tab2 = reader2.readAsTable();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
        
        String dayWithSmallestTempSpread = analyze(tab1, col1, col2,(x,y)-> Math.abs(x - y), false);
        System.out.printf("Day with smallest temperature spread : %s%n", dayWithSmallestTempSpread);

        String teamWithSmallestGoalSpread = analyze(tab2, col3, col4,(x,y)-> Math.abs(x - y), false);
        System.out.printf("Team with smallest goal spread       : %s%n", teamWithSmallestGoalSpread);
    }
    
    /**
     * Helper method that performs a generic analysis:
     * Processes two numeric columns with a given operation (e.g., subtraction, abs difference)
     * Finds the row indices with either min or max result values
     * Returns the first column values of those rows, joined as a string
     *
     * @param tab   Table containing the data
     * @param col1  First column name
     * @param col2  Second column name
     * @param op    Operation to apply on the two column values
     * @param isMax true to search for max values, false for min values
     * @return concatenated string of identifiers (from column 0) of the extreme rows
     */
    private static String analyze(Table tab, String col1, String col2,BiFunction<Double,Double,Double> op, boolean isMax) {
    	List<String> procdCol = tab.processColumns(col1, col2, op);
		List<Integer> rowIdxs = tab.getExtremeIndices(procdCol, isMax);
		List<String> result = new ArrayList<>();
		for(Integer idx : rowIdxs) {
			List<String> val = tab.getRow(idx);
			String name = val.get(0);
			result.add(name);
		}
		return String.join(", ", result);
    }
}

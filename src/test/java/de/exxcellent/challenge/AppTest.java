package de.exxcellent.challenge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Example JUnit 5 test case.
 * @author Benjamin Schmid <benjamin.schmid@exxcellent.de>
 */
class AppTest {

    private String successLabel = "not successful";
    private Table weatherTable;
    private Table footballTable;

    @BeforeEach
    void setUp() {
        successLabel = "successful";
        
        weatherTable = new Table(
                List.of("Day", "MxT", "MnT"),
                List.of(
                        List.of("1", "30", "20"),
                        List.of("2", "20", "15"),
                        List.of("3", "10", "9")
                )
        );

        footballTable = new Table(
                List.of("Team", "Goals", "GoalsAllowed"),
                List.of(
                        List.of("Arsenal", "70", "40"),
                        List.of("Liverpool", "60", "40"),
                        List.of("Chelsea", "90", "70")
                )
        );
    }

    @Test
    void aPointlessTest() {
        assertEquals("successful", successLabel, "My expectations were not met");
    }

    /**
     * Test analyzing the weather table by finding the day
     * with the smallest absolute difference between max and min temperature.
     */
    @Test
    void testAnalyzeWeather() {
        String day = invokeAnalyze(weatherTable, "MxT", "MnT", (x,y) -> Math.abs(x - y), false);
        assertEquals("3", day);
    }

    /**
     * Test analyzing the football table by finding the team(s)
     * with the smallest absolute difference between goals scored
     * and goals allowed.
     */
    @Test
    void testAnalyzeFootball() {
        String team = invokeAnalyze(footballTable, "Goals", "GoalsAllowed", (x,y) -> Math.abs(x - y), false);
        assertEquals("Liverpool, Chelsea", team);
    }

    /**
     * Helper method that performs a generic analysis to access the private function in App class:
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
    private static String invokeAnalyze(Table tab, String col1, String col2, BiFunction<Double, Double, Double> op, boolean isMax) {
        List<String> processedCol = tab.processColumns(col1, col2, op);
        List<Integer> rowIdxs = tab.getExtremeIndices(processedCol, isMax);

        List<String> result = new ArrayList<>();
        for (Integer idx : rowIdxs) {
            List<String> row = tab.getRow(idx);
            String name = row.get(0);
            result.add(name);
        }

        return String.join(", ", result);
    }

}
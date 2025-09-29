package de.exxcellent.challenge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Table class.
 */
class TableTest {

    private Table tab;

    /**
     * Sets up a sample Table instance before each test.
     */
    @BeforeEach
    void setUp() {
        List<String> headers = Arrays.asList("Day", "MaxTemp", "MinTemp");
        List<List<String>> rows = new ArrayList<>();
        rows.add(Arrays.asList("1", "30", "-15"));
        rows.add(Arrays.asList("2", "30", "15"));
        rows.add(Arrays.asList("3", "-10", "-20"));

        tab = new Table(new ArrayList<>(headers), new ArrayList<>(rows));
    }

    /**
     * Tests that getHeaders() returns a copy of the headers list.
     * Modifying the returned list should not affect the original headers in the Table.
     */
    @Test
    void testGetHeaders() {
        List<String> headers = tab.getHeaders();
        assertEquals(3, headers.size());
        assertEquals("Day", headers.get(0));

        // Modify the returned list
        headers.add("NewHeader");

        // Original headers should remain unchanged
        List<String> originalHeaders = tab.getHeaders();
        assertEquals(3, originalHeaders.size(), "Original headers should not be modified!");
    }

    /**
     * Tests that getRows() returns a deep copy of the rows.
     * Adding or modifying rows in the returned list should not affect the original table.
     */
    @Test
    void testGetRows() {
        List<List<String>> rows = tab.getRows();
        assertEquals(3, rows.size());
        assertEquals("1", rows.get(0).get(0));

        // Add a new row to the returned list
        rows.add(new ArrayList<>(Arrays.asList("4", "40", "10")));
        assertEquals(3, tab.getRows().size(), "Original rows should not be modified!");

        // Modify a value in a returned row
        rows.get(0).set(0, "Modified");
        assertEquals("1", tab.getRows().get(0).get(0), "Original row values should not be modified!");
    }

    /**
     * Test that getRow returns the correct row for a valid index.
     */
    @Test
    void testGetRowValid() {
        assertEquals(Arrays.asList("1", "30", "-15"), tab.getRow(0), "Should return the first row correctly");
    }

    /**
     * Test that getRow throws IllegalArgumentException for an invalid index.
     */
    @Test
    void testGetRowInvalid() {
        assertThrows(IllegalArgumentException.class, () -> tab.getRow(-1), "Should throw IllegalArgumentException for negative index");
        assertThrows( IllegalArgumentException.class, () -> tab.getRow(3), "Should throw IllegalArgumentException for index out of bounds");
    }
    
    /**
     * Test getting the column index by header name.
     */
    @Test
    void testGetColumnIndex() {
        assertEquals(1, tab.getColumnIndex("MaxTemp"));
        assertEquals(-1, tab.getColumnIndex("DoesNotExist"));
    }
    
    /**
     * Test getting a specific value from the table.
     */
    @Test
    void testGetValue() {
        assertEquals("15", tab.getValue(1, "MinTemp"));
    }
    
    /**
     * Test getting a valid column.
     */
    @Test
    void testGetColumnValid() {
        assertEquals(Arrays.asList("30", "30", "-10"), tab.getColumn("MaxTemp"));
    }

    /**
     * Test getting a column that does not exist.
     */
    @Test
    void testGetColumnInvalid() {
        assertThrows(IllegalArgumentException.class, () -> tab.getColumn("DoesNotExist"));
    }
    
    /**
     * Test adding a valid new column to the table.
     * Ensures the header is added and all row values are appended correctly.
     */
    @Test
    void testAddColumnValid() {
        List<String> newColValues = Arrays.asList("22.5", "23.0", "27.5");
        tab.addColumn("AvgTemp", newColValues);

        List<String> headers = tab.getHeaders();
        assertEquals(4, headers.size());
        assertEquals("AvgTemp", headers.get(3));

        List<List<String>> rows = tab.getRows();
        assertEquals("22.5", rows.get(0).get(3));
        assertEquals("23.0", rows.get(1).get(3));
        assertEquals("27.5", rows.get(2).get(3));
    }

    /**
     * Test adding a column with mismatched number of values.
     * Expects IllegalArgumentException to be thrown.
     */
    @Test
    void testAddColumnInvalidSize() {
        List<String> newColValues = Arrays.asList("22.5", "23.0"); 
        assertThrows(IllegalArgumentException.class, () -> tab.addColumn("AvgTemp", newColValues));
    }

    /**
     * Test adding a valid new row to the table.
     * Ensures the row is appended correctly and matches the header size.
     */
    @Test
    void testAddRowValid() {
        List<String> newRow = Arrays.asList("4", "25", "5");
        tab.addRow(newRow);

        List<List<String>> rows = tab.getRows();
        assertEquals(4, rows.size());
        assertEquals("4", rows.get(3).get(0));
        assertEquals("25", rows.get(3).get(1));
        assertEquals("5", rows.get(3).get(2));
    }

    /**
     * Test adding a row with mismatched number of values.
     * Expects IllegalArgumentException to be thrown.
     */
    @Test
    void testAddRowInvalidSize() {
        List<String> newRow = Arrays.asList("4", "25");
        assertThrows(IllegalArgumentException.class, () -> tab.addRow(newRow));
    }
    
    /**
     * Tests subtraction of two numeric columns.
     */
    @Test
    void testSubtraction() {
        BiFunction<Double, Double, Double> subtract = (x, y) -> x - y;
        List<String> result = tab.processColumns("MaxTemp", "MinTemp", subtract);

        assertEquals(Arrays.asList("45.0", "15.0", "10.0"), result);
    }

    /**
     * Tests behavior when one column contains non-numeric values.
     * Non-parsable entries should produce null in the result.
     */
    @Test
    void testNonNumericValues() {
    	List<String> headers = Arrays.asList("Day", "MaxTemp", "MinTemp");
        List<List<String>> rows = new ArrayList<>();
        rows.add(Arrays.asList("Monday", "30", "-15"));
        rows.add(Arrays.asList("Tuesday", "30", "15"));
        rows.add(Arrays.asList("Wednesday", "-10", "-20"));
        tab = new Table(new ArrayList<>(headers), new ArrayList<>(rows));
        BiFunction<Double, Double, Double> subtract = (x, y) -> x - y;
        List<String> result = tab.processColumns("Day", "MaxTemp", subtract);

        assertEquals(Arrays.asList(null, null, null), result);
    }

        /**
         * Test that the maximum value in "MaxTemp" is correctly identified.
         */
        @Test
        void testGetExtremeValueMax() {
            assertEquals(30.0, tab.getExtremeValue("MaxTemp", true));
        }

        /**
         * Test that the minimum value in "MinTemp" is correctly identified.
         */
        @Test
        void testGetExtremeValueMin() {
            assertEquals(-20, tab.getExtremeValue("MinTemp", false));
        }

        /**
         * Test that indices of maximum values in "MaxTemp" are correctly returned.
         */
        @Test
        void testGetExtremeIndicesMax() {
            List<Integer> indices = tab.getExtremeIndices("MaxTemp", true);
            assertEquals(Arrays.asList(0,1), indices);
        }

        /**
         * Test that indices of minimum values in "MinTemp" are correctly returned.
         */
        @Test
        void testGetExtremeIndicesMin() {
            List<Integer> indices = tab.getExtremeIndices("MinTemp", false);
            assertEquals(Arrays.asList(2), indices);
        }

        /**
         * Test getExtremeIndices with a direct column list for maximum value.
         */
        @Test
        void testGetExtremeIndicesWithColumnMax() {
            List<String> col = Arrays.asList("10", "20", "20", "5");
            List<Integer> indices = tab.getExtremeIndices(col, true);
            assertEquals(Arrays.asList(1, 2), indices);
        }

        /**
         * Test getExtremeIndices with a direct column list for minimum value.
         */
        @Test
        void testGetExtremeIndicesWithColumnMin() {
            List<String> col = Arrays.asList("10", "20", "20", "5");
            List<Integer> indices = tab.getExtremeIndices(col, false);
            assertEquals(Arrays.asList(3), indices);
        }

        /**
         * Test that invalid number strings in the column are ignored.
         */
        @Test
        void testGetExtremeIndicesWithInvalidNumbers() {
            List<String> col = Arrays.asList("10", "abc", "20");
            List<Integer> indices = tab.getExtremeIndices(col, true);
            assertEquals(Arrays.asList(2), indices);
        }
   

  

}

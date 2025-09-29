package de.exxcellent.challenge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
     * Test getting a specific value from the table.
     */
    @Test
    void testGetValue() {
        assertEquals("15", tab.getValue(1, "MinTemp"));
    }

    /**
     * Test getting the column index by header name.
     */
    @Test
    void testGetColumnIndex() {
        assertEquals(1, tab.getColumnIndex("MaxTemp"));
        assertEquals(-1, tab.getColumnIndex("DoesNotExist"));
    }
    

}

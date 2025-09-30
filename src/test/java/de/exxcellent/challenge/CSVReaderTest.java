package de.exxcellent.challenge;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for CSVReader class.
 */
class CSVReaderTest {

    /**
     * Tests that a normal CSV file is correctly read into a Table object.
     */
    @Test
    void testReadAsTable() throws IOException {
        CSVReader reader = new CSVReader("de/exxcellent/challenge/test.csv");
        Table tab = reader.readAsTable();

        // Verify headers
        List<String> headers = tab.getHeaders();
        assertEquals(3, headers.size());
        assertEquals("Day", headers.get(0));
        assertEquals("MaxTemp", headers.get(1));
        assertEquals("MinTemp", headers.get(2));

        // Verify rows
        List<List<String>> rows = tab.getRows();
        assertEquals(3, rows.size());
        assertEquals("1", rows.get(0).get(0));
        assertEquals("30", rows.get(1).get(1));
        assertEquals("15", rows.get(2).get(2));
    }

    /**
     * Tests that reading a non-existent file throws an IOException.
     */
    @Test
    void testReadAsTableFileNotFound() {
        CSVReader reader = new CSVReader("de/exxcellent/challenge/notFound.csv");
        assertThrows(IOException.class, reader::readAsTable);
    }

    /**
     * Tests that CSV files with a custom delimiter (e.g., semicolon) are correctly parsed.
     */
    @Test
    void testDelimiter() throws IOException {
        CSVReader reader = new CSVReader("de/exxcellent/challenge/test_semi.csv", ";");
        Table tab = reader.readAsTable();

        List<String> headers = tab.getHeaders();
        assertEquals("Day", headers.get(0));
        assertEquals("MaxTemp", headers.get(1));
        assertEquals("MinTemp", headers.get(2));
    }
}

package de.exxcellent.challenge;

import java.io.IOException;

/**
 * Abstract base class for reading tables from various sources (e.g., CSV, JSON, etc.).
 * Subclasses must implement the readAsTable method to return a Table object.
 */
abstract class TableReader {
    
    /**
     * Reads data from the source and returns it as a Table object.
     * 
     * @return Table representing the read table
     * @throws IOException if reading from the source fails
     */
    public abstract Table readAsTable() throws IOException;
}

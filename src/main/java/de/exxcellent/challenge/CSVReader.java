package de.exxcellent.challenge;

import java.io.IOException;

/**
 * CSVReader is a subclass of TableReader that reads CSV files as a Table.
 * It stores the file path and delimiter used for parsing.
 */
public class CSVReader extends TableReader {
    
    // Path to the CSV file
	private final String path;
    
    // Delimiter used to separate values in the CSV
    private final String delimiter;
    
    /**
     * Constructor that accepts a path and a custom delimiter.
     *
     * @param path Path to the CSV file
     * @param delimiter Delimiter used in the CSV file
     */
    public CSVReader(String path, String delimiter) {
        this.path = path;
        this.delimiter = delimiter;
    }
    
    /**
     * Constructor that accepts only the file path.
     * Uses a comma (,) as default delimiter.
     *
     * @param filePath Path to the CSV file
     */
    public CSVReader(String filePath) {
        this(filePath, ",");
    }
    
    /**
     * Returns the delimiter used for this CSVReader.
     *
     * @return CSV delimiter
     */
    public String getDelimiter() {
		return this.delimiter;
	}

    /**
     * Returns the file path of the CSV file.
     *
     * @return CSV file path
     */
	public String getPath() {
		return this.path;
	}

    /**
     * Reads the CSV file and returns it as a Table object.
     * Currently not implemented.
     *
     * @return Table representing the CSV file
     * @throws IOException if file reading fails
     */
	@Override
	public Table readAsTable() throws IOException {
		// TODO: implement CSV reading and return Table
		return null;
	}
    
}	

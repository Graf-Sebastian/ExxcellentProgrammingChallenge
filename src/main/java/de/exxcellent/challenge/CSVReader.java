package de.exxcellent.challenge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
	 * Reads the CSV file from the classpath and returns it as a Table object.
	 * The `path` is a relative path within the classpath, e.g., "de/exxcellent/challenge/weather.csv".
	 * The first line of the CSV contains the column headers.
	 * Missing values are allowed and will be represented as empty strings.
	 *
	 * @return Table representing the CSV file
	 * @throws IOException if the file cannot be found or read
	 */
	@Override
	public Table readAsTable() throws IOException {
	    List<String> headers = new ArrayList<>();
	    List<List<String>> rows = new ArrayList<>();

	    // Load file from classpath
	    InputStream is = getClass().getResourceAsStream("/" + path);
	    if (is == null) {
	        throw new IOException("File not found in classpath: " + path);
	    }

	    try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
	        String line;
	        boolean isFirstLine = true;

	        while ((line = br.readLine()) != null) {
	            line = line.trim();
	            
	            // Split the line using the delimiter; -1 keeps trailing empty strings
	            String[] values = line.split(delimiter, -1);

	            if (isFirstLine) {
	                // First line contains headers
	                for (String header : values) {
	                    headers.add(header.trim());
	                }
	                isFirstLine = false;
	            } else {
	                // Remaining lines are data rows
	                List<String> row = new ArrayList<>();
	                for (String val : values) {
	                    row.add(val.trim());
	                }
	                rows.add(row);
	            }
	        }
	    }

	    return new Table(headers, rows);
	}

    
}	

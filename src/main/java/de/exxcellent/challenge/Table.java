package de.exxcellent.challenge;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a table with headers and rows.
 * Each row is a list of strings.
 */
public class Table {
    
    // List of column headers
	private List<String> headers;
    
    // List of rows
    private List<List<String>> rows;
    
    /**
     * Constructor: Creates a table with given headers and rows.
     * Copies of the passed lists are made to protect the internal structure
     * from external modifications.
     *
     * @param headers List of column headers
     * @param rows List of rows
     */
    public Table(List<String> headers, List<List<String>> rows) {
        this.headers = new ArrayList<>(headers);
        this.rows = new ArrayList<>();
        for (List<String> row : rows) {
            this.rows.add(new ArrayList<>(row));
        }
    }
    
    /**
     * Returns the headers of the table.
     * A copy is returned to protect the internal list from external changes.
     *
     * @return List of headers
     */
    public List<String> getHeaders() {
        return new ArrayList<>(headers);
    }

    /**
     * Returns all rows of the table.
     * Each row is copied so that external modifications do not affect the table.
     *
     * @return List of rows
     */
    public List<List<String>> getRows() {
        List<List<String>> copy = new ArrayList<>();
        for (List<String> row : rows) {
            copy.add(new ArrayList<>(row)); // Create a copy of each row
        }
        return copy;
    }
    
    /**
     * Returns a copy of the row at the specified index.
     * A new list is returned to prevent external modification of the internal table data.
     *
     * @param rowIndex the index of the row to retrieve
     * @return a copy of the row at the specified index
     * @throws IllegalArgumentException if rowIndex is negative or >= number of rows
     */
    public List<String> getRow(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= rows.size()) {
            throw new IllegalArgumentException("Invalid row index: " + rowIndex);
        }
        return new ArrayList<>(rows.get(rowIndex));
    }
    
    /**
     * Returns the index of the specified column header.
     *
     * @param header the name of the column
     * @return the index of the column, or -1 if the column does not exist
     */
    public int getColumnIndex(String header) {
        return headers.indexOf(header);
    }

    
    /**
     * Returns the value at the specified row and column (by header name).
     *
     * @param rowIndex the index of the row
     * @param header the name of the column
     * @return the value as a String
     * @throws IllegalArgumentException if the header does not exist or the row index is invalid
     */
    public String getValue(int rowIndex, String header) {
        int colIndex = getColumnIndex(header);
        if (colIndex == -1) 
            throw new IllegalArgumentException("Column does not exist: " + header);

        return getRow(rowIndex).get(colIndex);
    }

    /**
     * Returns a column as a list of strings for the given header.
     *
     * @param header the name of the column
     * @return a List of strings representing the column values
     * @throws IllegalArgumentException if the header does not exist
     */
    public List<String> getColumn(String header) {
        int colIndex = getColumnIndex(header);
        if (colIndex == -1) 
            throw new IllegalArgumentException("Column does not exist: " + header);

        List<String> column = new ArrayList<>();
        for (List<String> row : rows) {
            column.add(row.get(colIndex));
        }
        return column;
    }


}
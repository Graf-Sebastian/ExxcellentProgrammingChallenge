package de.exxcellent.challenge;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

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
    
    /**
     * Adds a new column to the table with the specified header and values.
     * The number of values must match the current number of rows in the table.
     *
     * @param header The name of the new column
     * @param values List of values for the new column; must have the same size as the number of rows
     * @throws IllegalArgumentException if the number of values does not match the number of rows
     */
    public void addColumn(String header, List<String> values) {
        if (values.size() != rows.size()) 
            throw new IllegalArgumentException("Anzahl der Werte passt nicht zu den Zeilen!");
        headers.add(header);
        for (int i = 0; i < rows.size(); i++) {
            rows.get(i).add(values.get(i));
        }
    }

    /**
     * Adds a new row to the table.
     * The row must have the same number of values as the number of headers.
     *
     * @param row List of values for the new row
     * @throws IllegalArgumentException if the row size does not match the number of headers
     */
    public void addRow(List<String> row) {
        if (row.size() != headers.size()) 
            throw new IllegalArgumentException("Zeile hat zu wenig Spalten!");
        rows.add(new ArrayList<>(row));
    }
    
    /**
     * Applies a binary operation to two columns of the table and returns the result as a list of strings.
     * Each row is processed independently. If a value cannot be parsed as double, null is returned for that row.
     *
     * @param header1 Name of the first column
     * @param header2 Name of the second column
     * @param operator A BiFunction that defines the mathematical operation to perform on the two column values
     * @return List of results as strings, one per row
     * @throws IllegalArgumentException if one or both columns do not exist
     */
    public List<String> processColumns(String header1, String header2, BiFunction<Double, Double, Double> operator) {
        int idx1 = getColumnIndex(header1);
        int idx2 = getColumnIndex(header2);
        if (idx1 == -1 || idx2 == -1) 
            throw new IllegalArgumentException("Eine oder beiden Spalten existieren nicht!");

        List<String> result = new ArrayList<>();

        for (List<String> row : rows) {
            try {
                double val1 = Double.parseDouble(row.get(idx1));
                double val2 = Double.parseDouble(row.get(idx2));

                double calcRes = operator.apply(val1, val2);

                result.add(Double.toString(calcRes));
            } catch (NumberFormatException e) {
                result.add(null);
                System.out.println("Parsing String to Double not possible! " + e.getMessage());
            }
        }

        return result;
    }




}
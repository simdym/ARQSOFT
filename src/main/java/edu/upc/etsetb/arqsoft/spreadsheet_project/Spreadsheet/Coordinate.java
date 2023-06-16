package edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet;


import edu.upc.etsetb.arqsoft.spreadsheet.entities.BadCoordinateException;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * Represents the coordinates of a cell in the spreadsheet.
 * Defined by a row and a column, which are integers.
 * If given a string, it can be converted to a coordinate using a matcher.
 *
 *
 *
 */
public class Coordinate {
    private int row;
    private int col;
    private int hashCode;

    public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
        this.hashCode = Objects.hash(row, col);
    }

    public Coordinate(String cellId) throws BadCoordinateException {

        // Use regex pattern and matcher
        Pattern pattern = Pattern.compile("([A-Z]+)(\\d+)");
        Matcher matcher = pattern.matcher(cellId);

        if (!matcher.matches()) {

            // Coordinate does not follow regex mathcer
            throw new BadCoordinateException("Invalid coordinate format: " + cellId);
        }

        // Get row index and column as
        String colString = matcher.group(1); // With letters
        String rowString = matcher.group(2); // With digits

        // Convert character representation of column index to integer
        col = 0;
        for(int i = 0; i < colString.length(); i++) {
            col *= 26;
            col += colString.charAt(i) - 'A' + 1;
        }

        // -1 because 0-indexing
        col -= 1;
        row = Integer.parseInt(rowString) - 1;
        this.hashCode = Objects.hash(row, col);
    }


    // Necessary for retrieval from HashMap
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Coordinate that = (Coordinate) o;
        return row == that.getRow() && col == that.getCol();
    }

    // Necessary for retrieval from HashMap
    @Override
    public int hashCode() {
        return this.hashCode;
    }

    public int getRow() {return row;}
    public int getCol() {return col;
    }
}
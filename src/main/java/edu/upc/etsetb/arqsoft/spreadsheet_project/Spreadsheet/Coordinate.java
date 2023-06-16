package edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet;


import edu.upc.etsetb.arqsoft.spreadsheet_project.Exceptions.InvalidCellIDException;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Coordinate {
    private int row;
    private int col;
    private int hashCode;

    public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
        this.hashCode = Objects.hash(row, col);
    }

    public Coordinate(String cellId) throws InvalidCellIDException {
        Pattern pattern = Pattern.compile("([A-Z]+)(\\d+)");
        Matcher matcher = pattern.matcher(cellId);

        if (!matcher.matches()) {
            throw new InvalidCellIDException("Invalid coordinate format: " + cellId);
        }

        String colString = matcher.group(1); // With letters
        String rowString = matcher.group(2); // With digits

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
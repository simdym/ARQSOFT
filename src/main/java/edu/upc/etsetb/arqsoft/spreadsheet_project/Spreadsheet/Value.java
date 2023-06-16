package edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet;

import edu.upc.etsetb.arqsoft.spreadsheet.entities.NoNumberException;


/**
 *
 * Represents the value of a cell. IT can be a textValue or a numericalValue.
 */
public interface Value {
    public abstract Object getValue();

    public abstract double getValueAsDouble() throws NoNumberException;

    public abstract String getValueAsString();
}

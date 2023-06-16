package edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet;

import edu.upc.etsetb.arqsoft.spreadsheet.entities.NoNumberException;
/**
 * TextValue is a subclass of Value. It represents the value of a cell that is a text (TEXTCONTENT).
 * It contains the value of the cell as a string.
 */
public class TextValue implements Value {
    String value;
    public TextValue(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {return value;}

    @Override
    public String getValueAsString() { return value; }

    @Override
    public double getValueAsDouble() throws NoNumberException { throw new NoNumberException("This value is not a number"); }
}

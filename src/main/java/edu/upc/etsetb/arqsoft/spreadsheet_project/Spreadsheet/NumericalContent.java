package edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet;

/**
 * NumericalContent is a subclass of Content. It represents the content of a cell that is a number.
 */
public class NumericalContent extends Content {
    public NumericalContent(String value) {
        super(value, new NumericalValue(value));
    }
}

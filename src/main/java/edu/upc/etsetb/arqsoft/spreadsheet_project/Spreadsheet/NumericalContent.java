package edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet;

public class NumericalContent extends Content {
    public NumericalContent(String value) {
        super(value, new NumericalValue(value));
    }
}

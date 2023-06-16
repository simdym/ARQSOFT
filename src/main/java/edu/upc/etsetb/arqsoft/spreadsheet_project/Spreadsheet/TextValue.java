package edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet;

public class TextValue implements Value {
    String value;
    public TextValue(String value) {
        this.value = value;
    }

    public String getValue() {return value;}
}

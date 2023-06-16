package edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet;

public class TextContent extends Content {
    public TextContent(String value) {
        super(value, new TextValue(value));
    }
}

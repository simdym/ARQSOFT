package edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet;

/**
 * TextContent is a subclass of Content. It represents the content of a cell that is a text, in a string.
 */
public class TextContent extends Content {
    public TextContent(String value) {
        super(value, new TextValue(value));
    }
}

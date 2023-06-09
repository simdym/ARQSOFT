package edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet;


/**
 *
 * Represents the content of a cell.
 * The content can be a text, a value or a formula.
 *
 *
 */

public abstract class Content {
    protected String content;
    protected Value value;

    Content(String content, Value value) {
        this.content = content;
        this.value = value;
    }

    public String getContent() {return content;}
    public String getSaveableString() {return content; }
    public Value getValue() {return value;}


    public void setContent(String newContent) {content = newContent;}
    public void setValue(Value newValue) {value = newValue;}

}

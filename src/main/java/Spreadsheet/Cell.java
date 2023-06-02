package Spreadsheet;

public class Cell {
    Content content;

    Cell(Content content) {
        this.content = content;
    }

    public void setContent(Content content) {this.content = content;}
    public Content getContent() {
        return content;
    }
}

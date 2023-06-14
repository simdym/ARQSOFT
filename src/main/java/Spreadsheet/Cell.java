package Spreadsheet;

public class Cell implements Argument{
    Content content;

    Cell(Content content) {
        this.content = content;
    }

    public void setContent(Content content) {this.content = content;}
    public Content getContent() {
        return content;
    }
}

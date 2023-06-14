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

    public double getDoubleValue() {
        Object valueObj = content.getValue().getValue();
        if (valueObj instanceof Double) {
            return (double) valueObj;
        } else {
            throw new UnsupportedOperationException("Cell value cannot be converted to a numerical value.");
        }
    }


}

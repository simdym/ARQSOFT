package Spreadsheet;

import java.util.LinkedList;

public class Cell implements Argument,Operand{
    Content content;

    LinkedList<Cell> references = new LinkedList<Cell>();
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

    public void addCellReference(Cell cell) {
        references.add(cell);
        //System.out.println("addCellReference to cell, which has"+  references.size()+"references");
    }
    public void removeCellReference(Cell cell) {
        references.remove(cell);
    }

    public LinkedList<Cell> getCellReferences() {
        return references;
    }

}

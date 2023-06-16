package edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet;
import edu.upc.etsetb.arqsoft.spreadsheet_project.Formula.Argument;
import edu.upc.etsetb.arqsoft.spreadsheet_project.Formula.Operand;

import java.util.LinkedList;

public class Cell implements Argument, Operand {
    Content content;
    Coordinate coordinate;
    LinkedList<Cell> references = new LinkedList<Cell>();
    public Cell(Coordinate coordinate, Content content) {
        this.coordinate = coordinate;
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
    }
    public void removeCellReference(Cell cell) {
        references.remove(cell);
    }

    public LinkedList<Cell> getCellReferences() {
        return references;
    }
}

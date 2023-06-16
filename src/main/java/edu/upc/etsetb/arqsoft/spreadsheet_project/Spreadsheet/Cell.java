package edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet;
import edu.upc.etsetb.arqsoft.spreadsheet_project.Formula.Argument;
import edu.upc.etsetb.arqsoft.spreadsheet_project.Formula.Operand;

import java.util.LinkedList;


/**
 * Represents a cell in the spreadsheet.
 * A cell has a content and a coordinate.
 * The content can be a text, a value or a formula.
 * The coordinate is the position of the cell in the spreadsheet.
 */

public class Cell implements Argument, Operand {
    Content content;
    Coordinate coordinate;

    LinkedList<Cell> references = new LinkedList<Cell>();


    public Cell(Coordinate coordinate, Content content) {//constructor
        this.coordinate = coordinate;
        this.content = content;
    }

    public void setContent(Content content) {this.content = content;} //setContent of the cell
    public Content getContent() {
         return content;
    }

    public double getDoubleValue() { //get the numerical value of the cell as a double
        Object valueObj = content.getValue().getValue();
        if (valueObj instanceof Double) {
            return (double) valueObj;
        } else {
            throw new UnsupportedOperationException("Cell value cannot be converted to a numerical value.");
        }
    }

    public void addCellReference(Cell cell) { //Used for dependencies adds cell to the list of references

        references.add(cell);
    }
    public void removeCellReference(Cell cell) {//Used for dependencies removes cell from the list of references
        references.remove(cell);
    }

    public LinkedList<Cell> getCellReferences() {//Used for dependencies returns the list of references
        return references;
    }
}

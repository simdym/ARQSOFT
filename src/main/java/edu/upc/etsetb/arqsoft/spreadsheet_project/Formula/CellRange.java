package edu.upc.etsetb.arqsoft.spreadsheet_project.Formula;
import edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet.Cell;
import edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet.Coordinate;
import edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet.Spreadsheet;

import java.util.LinkedList;
/**
 *
 * CellRange class. It is used to store the range of cells that are used in a formula, as an argument.
 * Defined by topleft coordinate and bottomright coordinate.
 *
 * Method listOfCells() returns a list of cells that are in the range.
 */
public class CellRange{
    private Coordinate topLeftCoordinate;
    private Coordinate bottomRightCoordinate;
    private Spreadsheet spreadsheet;

    public CellRange(Coordinate topLeftCoordinate, Coordinate bottomRightCoordinate, Spreadsheet spreadsheet) {
        this.topLeftCoordinate = topLeftCoordinate;
        this.bottomRightCoordinate = bottomRightCoordinate;
        this.spreadsheet = spreadsheet;
    }

    public LinkedList<Cell> listOfCells() { //Transforms coordinates to integers and iterates through them to get the cells.
        int topRow = topLeftCoordinate.getRow();
        int bottomRow = bottomRightCoordinate.getRow();
        int topCol = topLeftCoordinate.getCol();
        int bottomCol = bottomRightCoordinate.getCol();

        LinkedList<Cell> cells = new LinkedList<>();

        for (int currentRow = topRow; currentRow <= bottomRow; currentRow++) {
            for (int currentCol = topCol; currentCol <= bottomCol; currentCol++) {
                Coordinate currentCoord = new Coordinate(currentRow, currentCol);
                Cell cell = spreadsheet.getCell(currentCoord);
                if (cell != null) {
                    cells.add(cell);
                }
            }
        }
        return cells;
    }
}

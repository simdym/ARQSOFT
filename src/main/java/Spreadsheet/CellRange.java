package Spreadsheet;
import java.util.LinkedList;
import java.util.LinkedList;

public class CellRange{
    private Coordinate topLeftCoordinate;
    private Coordinate bottomRightCoordinate;
    private Spreadsheet spreadsheet;

    public CellRange(Coordinate topLeftCoordinate, Coordinate bottomRightCoordinate, Spreadsheet spreadsheet) {
        this.topLeftCoordinate = topLeftCoordinate;
        this.bottomRightCoordinate = bottomRightCoordinate;
        this.spreadsheet = spreadsheet;
    }

    public LinkedList<Cell> listOfCells() {
        int topRow = topLeftCoordinate.getRow();
        int bottomRow = bottomRightCoordinate.getRow();
        int topCol = topLeftCoordinate.getCol();
        int bottomCol = bottomRightCoordinate.getCol();
        System.out.println(topRow+","+bottomRow+";"+topCol+","+bottomCol);
        LinkedList<Cell> cells = new LinkedList<>();

        for (int currentRow = topRow; currentRow <= bottomRow; currentRow++) {
            for (int currentCol = topCol; currentCol <= bottomCol; currentCol++) {
                Coordinate currentCoord = new Coordinate(currentRow, currentCol);
                Cell cell = spreadsheet.getCell(currentCoord);
                System.out.println("i");
                if (cell != null) {

                    cells.add(cell);
                }
            }
        }
        return cells;
    }
}

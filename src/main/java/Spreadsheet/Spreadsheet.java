package Spreadsheet;
import java.util.HashMap;

public class Spreadsheet {
    private String filename;
    private HashMap<Coordinate, Cell> cells;

    public Spreadsheet() {
        cells = new HashMap<Coordinate, Cell>();
    }
    public void updateAllCells(){}

    public int getMaxRow() {
        int maxRow = 0;
        for(Coordinate coordinate: cells.keySet()) {
            if(coordinate.getRow() > maxRow) {
                maxRow = coordinate.getRow();
            }
        }

        return maxRow;
    }

    public int getMaxColumn() {
        int maxColumn = 0;
        for(Coordinate coordinate: cells.keySet()) {
            if(coordinate.getCol() > maxColumn) {
                maxColumn = coordinate.getCol();
            }
        }

        return maxColumn;
    }

    private boolean isEmpty(int row, int col) {
        Coordinate coordinate = new Coordinate(row, col);
        return cells.containsKey(coordinate);
    }

    public void updateContent(Coordinate coordinate, Content content) {
        Cell cell = cells.get(coordinate);
        if(cell != null) {
            cell.setContent(content);
        } else {
            Cell newCell = new Cell(content);
            cells.put(coordinate, newCell);
        }
    }


    public Content getContent(Coordinate coordinate) {
        Cell cell = cells.get(coordinate);
        if (cell == null) {
            return null;
        } else {
            return cell.getContent();
        }
    }
}

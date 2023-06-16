package edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet;
import java.util.HashMap;
import java.util.Set;

public class Spreadsheet {
    private String filename;
    private HashMap<Coordinate, Cell> cells;

    public Spreadsheet() {
        cells = new HashMap<Coordinate, Cell>();
    }

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

    public Cell getCell(Coordinate coordinate) {
        return cells.get(coordinate);
    }

    public Set<Coordinate> getCoordinates() {
        return cells.keySet();
    }
}

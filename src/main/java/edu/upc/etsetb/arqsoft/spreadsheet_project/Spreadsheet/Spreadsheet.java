package edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet;
import java.util.*;

public class Spreadsheet {
    private String filename;
    private HashMap<Coordinate, Cell> cells;

    public Spreadsheet() {
        cells = new HashMap<Coordinate, Cell>();
    }

    public ArrayList<Integer> getRelevantRows() {
        LinkedHashSet<Integer> relevantRowsSet = new LinkedHashSet<Integer>();
        for (Coordinate coordinate : cells.keySet()) {
            relevantRowsSet.add(coordinate.getRow());
        }
        ArrayList<Integer> relevantRows = new ArrayList<Integer>(relevantRowsSet);
        Collections.sort(relevantRows);
        return relevantRows;
    }

    public ArrayList<Integer> getRelevantCols() {
        LinkedHashSet<Integer> relevantColsSet = new LinkedHashSet<Integer>();
        for (Coordinate coordinate : cells.keySet()) {
            relevantColsSet.add(coordinate.getCol());
        }
        ArrayList<Integer> relevantCols = new ArrayList<Integer>(relevantColsSet);
        Collections.sort(relevantCols);
        return relevantCols;
    }

    public int getMaxRow() {
        int maxRow = 0;
        for (Coordinate coordinate : cells.keySet()) {
            if (coordinate.getRow() > maxRow) {
                maxRow = coordinate.getRow();
            }
        }

        return maxRow;
    }

    public int getMaxColumn() {
        int maxColumn = 0;
        for (Coordinate coordinate : cells.keySet()) {
            if (coordinate.getCol() > maxColumn) {
                maxColumn = coordinate.getCol();
            }
        }

        return maxColumn;
    }

    public int getMaxWidth() {
        int maxWidth = 0;
        for(Cell cell: cells.values()) {
            int length = cell.getContent().getContent().length();
            if(length > maxWidth) {
                maxWidth = length;
            }
        }
        return maxWidth;
    }

    public void updateContent(Coordinate coordinate, Content content) {
        Cell cell = cells.get(coordinate);
        if(cell != null) {
            cell.setContent(content);
        } else {
            Cell newCell = new Cell(coordinate, content);
            cells.put(coordinate, newCell);
        }
    }

    public String getContentStr(Coordinate coordinate) {
        Cell cell = cells.get(coordinate);
        if (cell == null) {
            return "";
        } else {
            return cell.getContent().getContent();
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

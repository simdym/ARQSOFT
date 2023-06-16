package edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet;
import java.util.*;

public class Spreadsheet {
    private HashMap<Coordinate, Cell> cells;

    public Spreadsheet() {
        cells = new HashMap<Coordinate, Cell>();
    }

    public ArrayList<Integer> getRelevantRows() {
        LinkedHashSet<Integer> relevantRowsSet = new LinkedHashSet<Integer>();

        // Add all row indices
        for (Coordinate coordinate : cells.keySet()) {
            relevantRowsSet.add(coordinate.getRow());
        }

        // Sort relevant rows
        ArrayList<Integer> relevantRows = new ArrayList<Integer>(relevantRowsSet);
        Collections.sort(relevantRows);
        return relevantRows;
    }

    public ArrayList<Integer> getRelevantCols() {
        LinkedHashSet<Integer> relevantColsSet = new LinkedHashSet<Integer>();

        // Add all coordinate indices
        for (Coordinate coordinate : cells.keySet()) {
            relevantColsSet.add(coordinate.getCol());
        }

        // Sort relevant columns
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

        // Get the maximum width of value in cell
        int maxWidth = 0;
        for(Cell cell: cells.values()) {
            int length = cell.getContent().getValue().getValueAsString().length();
            if(length > maxWidth) {
                maxWidth = length;
            }
        }
        return maxWidth;
    }

    public void updateContent(Coordinate coordinate, Content content) {

        // Get cell from coordinate
        Cell cell = cells.get(coordinate);

        if(cell != null) {

            // If cell exist update content
            cell.setContent(content);
        } else {

            // Create new cell
            Cell newCell = new Cell(coordinate, content);
            cells.put(coordinate, newCell);
        }
    }

    public Content getContent(Coordinate coordinate) {

        // Get cell from coordinate
        Cell cell = cells.get(coordinate);
        if (cell == null) {

            // If cell does not exist return null
            return null;
        } else {

            // Get content from cell if it exist
            return cell.getContent();
        }
    }

    public Cell getCell(Coordinate coordinate) {
        return cells.get(coordinate);
    }
}

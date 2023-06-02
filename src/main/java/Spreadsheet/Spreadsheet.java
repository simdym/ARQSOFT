package Spreadsheet;
import java.util.HashMap;

public class Spreadsheet {
    private String filename;
    private HashMap<Coordinate, Cell> cells;

    public Spreadsheet() {
        cells = new HashMap<Coordinate, Cell>();
    }
    public void updateAllCells(){}

    //private int GetMaxRow(){}

    //private int GetMaxColumn(){}

    //private boolean isEmpty(int row, int col){}
    public void spreadsheetToS2V(){}

    public void deleteCell(String cellID){}

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
        return cells.get(coordinate).getContent();
    }



}

package Spreadsheet;

import Spreadsheet.Cmd.Cmd;
import org.jetbrains.annotations.NotNull;

public class Controller {
    private ContentFactory contentFactory;

    public Controller() {
        contentFactory = new ContentFactory();
    }

    public void run() {}
    private void processUserCommand(Cmd cmd){}
    private void createNewSpreadsheet(){}
    private void loadExistingSpreadsheet(String filepath){}
    private void saveSpreadsheet(String filename){}
    private void modifyCellContent(Spreadsheet ss, String cellId, String newValue){
        Coordinate cellCoor = new Coordinate(cellId);
        Content newContent = contentFactory.createContent(newValue);
        ss.updateContent(cellCoor, newContent);
    }

    //private double computeFormula(Cell cell){}
    private void deleteContent(String cellId){}
    //public Content retrieveCellContent(Coordinate coordinate){}




}

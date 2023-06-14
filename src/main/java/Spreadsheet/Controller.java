package Spreadsheet;

import Spreadsheet.Cmd.Cmd;
import Spreadsheet.Cmd.ECmd;

public class Controller {
    private Spreadsheet spreadsheet;
    private ContentFactory contentFactory;
    private UI ui;
    private FileManager fileManager;

    public Controller() {
        spreadsheet = new Spreadsheet();
        contentFactory = new ContentFactory();
        ui = new UI();
        fileManager = new FileManager();
    }

    public void run() {
        Spreadsheet ss = null;
        Cmd cmd = null;

        while(true) {
            try {
                cmd = ui.askForCmd();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            processUserCommand(cmd);
        }

    }
    private void processUserCommand(Cmd cmd){
        switch (cmd.getType()) {
            case "RF":

                break;
            case "C":
                createNewSpreadsheet();
                break;
            case "E":
                modifyCellContent(((ECmd) cmd).getCellID(), ((ECmd) cmd).getContentStr());
                break;
            case "L":

                break;
            case "S":

                break;
        }
    }

    private void createNewSpreadsheet(){
        spreadsheet = new Spreadsheet();
    }
    private void loadExistingSpreadsheet(String filepath) {

    }
    private void saveSpreadsheet(String filename){}
    private void modifyCellContent(String cellId, String newValue){
        Coordinate cellCoor = new Coordinate(cellId);
        Content newContent = contentFactory.createContent(newValue);
        spreadsheet.updateContent(cellCoor, newContent);
    }

    //private double computeFormula(Cell cell){}
    private void deleteContent(String cellId){}
    //public Content retrieveCellContent(Coordinate coordinate){}




}

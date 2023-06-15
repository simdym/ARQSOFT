package Spreadsheet;

import Spreadsheet.Cmd.Cmd;
import Spreadsheet.Cmd.CmdFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    private Spreadsheet spreadsheet;
    private UI ui;
    private FileManager fileManager;

    public Controller() {
        spreadsheet = new Spreadsheet();
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
        switch (cmd.getCmdType()) {
            case RUN_FROM_FILE:
                try {
                    runCommandsFromFile(cmd.getArgument(0));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case CREATE_SPREADSHEET:
                createNewSpreadsheet();
                break;
            case EDIT_CELL:
                modifyCellContent(cmd.getArgument(0), cmd.getArgument(1));
                break;
            case LOAD_SPREADSHEET:
                try {
                    loadExistingSpreadsheet(cmd.getArgument(0));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case SAVE_SPREADSHEET:
                try {
                    saveSpreadsheet(cmd.getArgument(0));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
        }
    }

    private void runCommandsFromFile(String filepath) throws FileNotFoundException {
        ArrayList<Cmd> commands = CmdFactory.readCmdFromFile(filepath);
        for(int i = 0; i < commands.size(); i++) {
            processUserCommand(commands.get(i));
        }
    }

    private void createNewSpreadsheet(){
        spreadsheet = new Spreadsheet();
    }
    private void loadExistingSpreadsheet(String filepath) throws FileNotFoundException {
        fileManager.loadSpreadsheet(filepath, spreadsheet);
    }
    private void saveSpreadsheet(String filepath) throws IOException {
        fileManager.saveSpreadsheet(spreadsheet, filepath);
    }
    private void modifyCellContent(String cellId, String newValue){
        Coordinate cellCoor = new Coordinate(cellId);
        Content newContent = ContentFactory.createContent(newValue);
        spreadsheet.updateContent(cellCoor, newContent);
    }

    //private double computeFormula(Cell cell){}
    private void deleteContent(String cellId){}
    //public Content retrieveCellContent(Coordinate coordinate){}




}

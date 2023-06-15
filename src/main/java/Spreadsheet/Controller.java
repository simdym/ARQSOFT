package Spreadsheet;
import Spreadsheet.Cmd.Cmd;
import Spreadsheet.Cmd.ECmd;
import Spreadsheet.Cmd.LCmd;
import Spreadsheet.Exceptions.EvaluationException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

public class Controller {
    private Spreadsheet spreadsheet;
    private ContentFactory contentFactory;
    private UI ui;
    private FileManager fileManager;

    private FormulaComponentFabricator formulaComponentFabricator;
    private PostFixEvaluator postfixEvaluator;

    private PostFixGenerator postfixGenerator;

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
                try {
                    loadExistingSpreadsheet(((LCmd) cmd).getFilepath());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "S":
                try {
                    saveSpreadsheet(((LCmd) cmd).getFilepath());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
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
        Content newContent = contentFactory.createContent(newValue);
        spreadsheet.updateContent(cellCoor, newContent);
    }

    private void updateCellValue(Cell cellToModify) {
        Content content = cellToModify.getContent();
        if (content != null && content instanceof FormulaContent) {
            FormulaContent formula = (FormulaContent) content;
            LinkedList<Tokenizer.Token> postfixExpression = formula.getPostfixExpression();
            LinkedList<FormulaComponent> formulaCompExpression = formulaComponentFabricator.fabricateComponentList(postfixExpression, spreadsheet);

            try {
                double result = postfixEvaluator.evaluatePostfix(formulaCompExpression);
                content.setValue(new NumericalValue(result));

            } catch (EvaluationException ex) {
                String result2 ="NaN";
                content.setValue(new TextValue(result2));
            }

        }

    }
    private void deleteContent(String cellId){}
    //public Content retrieveCellContent(Coordinate coordinate){}




}


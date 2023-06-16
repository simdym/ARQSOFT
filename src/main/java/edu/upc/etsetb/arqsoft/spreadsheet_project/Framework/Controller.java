package edu.upc.etsetb.arqsoft.spreadsheet_project.Framework;
import edu.upc.etsetb.arqsoft.spreadsheet.entities.BadCoordinateException;
import edu.upc.etsetb.arqsoft.spreadsheet.entities.NoNumberException;
import edu.upc.etsetb.arqsoft.spreadsheet.usecases.marker.ISpreadsheetControllerForChecker;
import edu.upc.etsetb.arqsoft.spreadsheet.usecases.marker.ReadingSpreadSheetException;
import edu.upc.etsetb.arqsoft.spreadsheet.usecases.marker.SavingSpreadSheetException;
import edu.upc.etsetb.arqsoft.spreadsheet_project.Cmd.CmdFactory;
import edu.upc.etsetb.arqsoft.spreadsheet_project.Cmd.Cmd;
import edu.upc.etsetb.arqsoft.spreadsheet_project.Exceptions.CircularDependencyException;
import edu.upc.etsetb.arqsoft.spreadsheet_project.Exceptions.EvaluationException;
import edu.upc.etsetb.arqsoft.spreadsheet_project.Exceptions.ParserException;
import edu.upc.etsetb.arqsoft.spreadsheet_project.Formula.*;
import edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

import java.util.ArrayList;
import java.util.List;
public class Controller implements ISpreadsheetControllerForChecker {
    private Spreadsheet spreadsheet;
    private UI ui;
    private FileManager fileManager;

    private FormulaComponentFabricator formulaComponentFabricator = new FormulaComponentFabricator();
    private PostFixEvaluator postfixEvaluator = new PostFixEvaluator();

    private PostFixGenerator postfixGenerator = new PostFixGenerator();

    private Tokenizer tokenizer  = new Tokenizer();
    private Parser parser = new Parser();


    public Controller() {
        spreadsheet = new Spreadsheet();
        ui = new UI();
        fileManager = new FileManager();
    }

    public void run() {
        while(true) {
            Cmd cmd = null;
            try {
                cmd = ui.askForCmd();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            processUserCommand(cmd);
            ui.displaySpreadsheet(spreadsheet);
        }

    }
    private void processUserCommand(Cmd cmd){
        if(cmd == null) {
            return;
        }
        switch (cmd.getCmdType()) {
            case RUN_FROM_FILE -> {
                System.out.println("Running commands from file: " + cmd.getArgument(0) + "...");
                try {
                    runCommandsFromFile(cmd.getArgument(0));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            case CREATE_SPREADSHEET -> {
                System.out.println("Creating new spreadsheet...");
                createNewSpreadsheet();
            }
            case EDIT_CELL -> {
                System.out.println("Updating cell " + cmd.getArgument(0) + " with new content: " + cmd.getArgument(1) + "...");
                setCellContent(cmd.getArgument(0), cmd.getArgument(1));
            }
            case LOAD_SPREADSHEET -> {
                System.out.println("Loading spreadsheet from file: " + cmd.getArgument(0) + "...");
                try {
                    readSpreadSheetFromFile(cmd.getArgument(0));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            case SAVE_SPREADSHEET -> {
                System.out.println("Saving spreadsheet to file: " + cmd.getArgument(0) + "...");
                try {
                    saveSpreadSheetToFile(cmd.getArgument(0));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            default -> {
            }
        }
    }

    private void runCommandsFromFile(String filepath) throws FileNotFoundException {
        ArrayList<Cmd> commands = CmdFactory.readCmdFromFile(filepath);
        for (Cmd command : commands) {
            processUserCommand(command);
        }
    }

    private void createNewSpreadsheet(){
        spreadsheet = new Spreadsheet();
    }

    @Override
    public void readSpreadSheetFromFile(String nameInUserDir) throws ReadingSpreadSheetException {
        String userDir = System.getProperty("user.dir");
        String pathToLoad = new StringBuilder(userDir).append('\\').append(nameInUserDir).toString();
        try {
            fileManager.loadSpreadsheet(pathToLoad, spreadsheet);
            updateAllCells();
        } catch (FileNotFoundException e) {
            throw new ReadingSpreadSheetException(e.getMessage());
        }
    }

    @Override
    public void saveSpreadSheetToFile(String nameInUserDir) throws SavingSpreadSheetException {
        String userDir = System.getProperty("user.dir");
        String pathToLoad = new StringBuilder(userDir).append('\\').append(nameInUserDir).toString();
        try {
            fileManager.saveSpreadsheet(spreadsheet, pathToLoad);
        } catch (IOException e) {
            throw new SavingSpreadSheetException(e.getMessage());
        }
    }

    @Override
    public void setCellContent(String cellCoord, String strContent) {
        Coordinate cellCoor = new Coordinate(cellCoord);
        Cell oldcell = spreadsheet.getCell(cellCoor);
        Content previousContent;
        if (oldcell != null) {
            previousContent = oldcell.getContent();
        } else {
            previousContent = null;// Exit the method or do appropriate error handling
        }
        Content newContent = ContentFactory.createContent(strContent);
        spreadsheet.updateContent(cellCoor, newContent);
        Cell cell= spreadsheet.getCell(cellCoor);
        Content currentContent = cell.getContent();

        if ((currentContent instanceof FormulaContent)) {
            FormulaContent formula = (FormulaContent) currentContent;
            try {
                tokenizer.tokenize(formula.getContent());
            } catch (Tokenizer.ParserException e) {
                formula.setValue(new TextValue("SYNTAX ERROR"));
                throw new ParserException(e.getMessage());
            }
            LinkedList<Tokenizer.Token> tokens = tokenizer.getTokens();
            try {
                parser.setSpreadsheet(spreadsheet);
                parser.setTokens(tokens);
                parser.parse();
            } catch (ParserException e) {
                formula.setValue(new TextValue("SYNTAX ERROR"));
                throw new ParserException(e.getMessage());
            }

            LinkedList<Tokenizer.Token> parsedTokens = parser.getParsedTokens();
            List<Cell> dependencies = parser.getCellDependencies();
            try {
                parser.checkCircularDependencies(cell,dependencies);
            } catch (CircularDependencyException e) {
                cell.setContent(previousContent);
                throw new CircularDependencyException(e.getMessage());
            }
            formula.setDependentCells(dependencies);
            LinkedList<Tokenizer.Token> postfixExpression= postfixGenerator.generatePostfix(parsedTokens);
            formula.setPostfixExpression(postfixExpression);
            formulaComponentFabricator.setSpreadsheet(spreadsheet);
            LinkedList<FormulaComponent> formulaCompExpression = formulaComponentFabricator.fabricateComponentList(postfixExpression);

            try {
                double result = postfixEvaluator.evaluatePostfix(formulaCompExpression);
                formula.setValue(new NumericalValue(result));

            } catch (EvaluationException ex) {
                String result2 ="NaN";
                formula.setValue(new TextValue(result2));
            }
        }


        if (previousContent != null) {
            if (previousContent instanceof FormulaContent) {
                FormulaContent previousFormula = (FormulaContent) previousContent;
                List<Cell> previousDependencies = previousFormula.getDependentCells();

                for (Cell dependentCell : previousDependencies) {
                    dependentCell.removeCellReference(cell);
                }
            }
        }

        if (currentContent instanceof FormulaContent) {
            FormulaContent currentFormula = (FormulaContent) currentContent;
            List<Cell> currentDependencies = currentFormula.getDependentCells();

            for (Cell dependentCell : currentDependencies) {
                dependentCell.addCellReference(cell);
            }
        }

        LinkedList<Cell> references = new LinkedList<>();
        references.addAll(cell.getCellReferences());
        System.out.println("Updated cell has"+references.size()+"references");
        while (!references.isEmpty()) {
            Cell cellToUpdate = references.poll();
            references.addAll(cellToUpdate.getCellReferences());
            this.updateCellValue(cellToUpdate);
        }
    }

    @Override
    public double getCellContentAsDouble(String coord) throws BadCoordinateException, NoNumberException {
        Coordinate cellCord = new Coordinate(coord);
        return spreadsheet.getCell(cellCord).getContent().getValue().getValueAsDouble();
    }

    @Override
    public String getCellContentAsString(String coord) throws BadCoordinateException {
        Coordinate cellCord = new Coordinate(coord);
        return spreadsheet.getCell(cellCord).getContent().getContent();
    }

    @Override
    public String getCellFormulaExpression(String coord) throws BadCoordinateException {
        Coordinate cellCord = new Coordinate(coord);
        Content content = spreadsheet.getCell(cellCord).getContent();
        if(content instanceof FormulaContent) {
            return content.getContent().substring(1);
        } else {
            throw new BadCoordinateException("The cell is not a formula");
        }

    }

    private void updateCellValue(Cell cellToModify) {
        Content content = cellToModify.getContent();
        if (content instanceof FormulaContent) {
            FormulaContent formula = (FormulaContent) content;
            LinkedList<Tokenizer.Token> postfixExpression = formula.getPostfixExpression();
            formulaComponentFabricator.setSpreadsheet(spreadsheet);
            LinkedList<FormulaComponent> formulaCompExpression = formulaComponentFabricator.fabricateComponentList(postfixExpression);

            try {
                double result = postfixEvaluator.evaluatePostfix(formulaCompExpression);
                content.setValue(new NumericalValue(result));

            } catch (EvaluationException ex) {
                String result2 ="NaN";
                content.setValue(new TextValue(result2));
            }

        }
    }

    private void updateAllCells() {
        for(Coordinate coordinate: spreadsheet.getCoordinates()) {
            Cell cell = spreadsheet.getCell(coordinate);
            updateCellValue(cell);
        }
    }
}


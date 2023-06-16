package edu.upc.etsetb.arqsoft.spreadsheet_project.Framework;
import edu.upc.etsetb.arqsoft.spreadsheet.entities.BadCoordinateException;
import edu.upc.etsetb.arqsoft.spreadsheet.entities.CircularDependencyException;
import edu.upc.etsetb.arqsoft.spreadsheet.entities.ContentException;
import edu.upc.etsetb.arqsoft.spreadsheet.entities.NoNumberException;
import edu.upc.etsetb.arqsoft.spreadsheet.usecases.marker.ISpreadsheetControllerForChecker;
import edu.upc.etsetb.arqsoft.spreadsheet.usecases.marker.ReadingSpreadSheetException;
import edu.upc.etsetb.arqsoft.spreadsheet.usecases.marker.SavingSpreadSheetException;
import edu.upc.etsetb.arqsoft.spreadsheet_project.Cmd.CmdFactory;
import edu.upc.etsetb.arqsoft.spreadsheet_project.Cmd.Cmd;
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
                // Ask user for command
                cmd = ui.askForCmd();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            // Do command
            processUserCommand(cmd);
            ui.displaySpreadsheet(spreadsheet);
        }

    }
    private void processUserCommand(Cmd cmd){
        if(cmd == null) {
            return;
        }
        switch (cmd.getCmdType()) {

            //Run commands from file
            case RUN_FROM_FILE -> {
                System.out.println("Running commands from file: " + cmd.getArgument(0) + "...");
                try {
                    runCommandsFromFile(cmd.getArgument(0));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            // Creat new spreadsheet
            case CREATE_SPREADSHEET -> {
                System.out.println("Creating new spreadsheet...");
                createNewSpreadsheet();
            }

            // Edit cell content
            case EDIT_CELL -> {
                System.out.println("Updating cell " + cmd.getArgument(0) + " with new content: " + cmd.getArgument(1) + "...");
                try {
                    setCellContent(cmd.getArgument(0), cmd.getArgument(1));
                } catch (ContentException e) {
                    System.out.println(e.getMessage());
                } catch (CircularDependencyException e) {
                    System.out.println(e.getMessage());
                } catch (BadCoordinateException e) {
                    System.out.println(e.getMessage());
                }
            }

            // Load spreadsheet from file
            case LOAD_SPREADSHEET -> {
                System.out.println("Loading spreadsheet from file: " + cmd.getArgument(0) + "...");
                try {
                    readSpreadSheetFromFile(cmd.getArgument(0));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            // Save spreadsheet to file
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
        // Get commands from file
        ArrayList<Cmd> commands = CmdFactory.readCmdFromFile(filepath);

        // Run commands
        for (Cmd command : commands) {
            processUserCommand(command);
        }
    }

    private void createNewSpreadsheet(){
        spreadsheet = new Spreadsheet();
    }

    @Override
    public void readSpreadSheetFromFile(String nameInUserDir) throws ReadingSpreadSheetException, CircularDependencyException {
        // Get project root folder
        String userDir = System.getProperty("user.dir");
        
        // Append filename
        String loadPath = new StringBuilder(userDir).append('\\').append(nameInUserDir).toString();

        try {
            // Get content from file
            ArrayList<String[]> rows = fileManager.loadSpreadsheet(loadPath);

            // Read each line of file
            for(int row = 0; row < rows.size(); row++) {
                String[] rowContents = rows.get(row);

                // For each element in the array create a content if it is not empty
                for(int col = 0; col < rowContents.length; col++) {
                    if(rowContents[col] != "") {
                        Coordinate coordinate = new Coordinate(row, col);
                        try {
                            setCellContentFromCoordinate(coordinate, rowContents[col]);
                        } catch (Exception e) {
                            throw new ReadingSpreadSheetException(e.getMessage());
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new ReadingSpreadSheetException(e.getMessage());
        } catch (EvaluationException e) {
            throw new ReadingSpreadSheetException(e.getMessage());
        }
    }

    @Override
    public void saveSpreadSheetToFile(String nameInUserDir) throws SavingSpreadSheetException {
        // Get project root folder
        String userDir = System.getProperty("user.dir");

        // Append filename
        String pathToLoad = new StringBuilder(userDir).append('\\').append(nameInUserDir).toString();
        try {
            // Save to file
            fileManager.saveSpreadsheet(spreadsheet, pathToLoad);
        } catch (IOException e) {
            throw new SavingSpreadSheetException(e.getMessage());
        }
    }

    @Override
    public void setCellContent(String cellCoord, String strContent) throws ContentException, BadCoordinateException, CircularDependencyException {
        Coordinate coordinate = new Coordinate(cellCoord);
        setCellContentFromCoordinate(coordinate, strContent);
    }

    public void setCellContentFromCoordinate(Coordinate cellCoord, String strContent) throws ContentException, BadCoordinateException, CircularDependencyException {
        Cell oldcell = spreadsheet.getCell(cellCoord);
        Content previousContent;
        if (oldcell != null) {
            previousContent = oldcell.getContent();
        } else {
            previousContent = null;// Exit the method or do appropriate error handling
        }
        Content newContent = ContentFactory.createContent(strContent);
        spreadsheet.updateContent(cellCoord, newContent);
        Cell cell= spreadsheet.getCell(cellCoord);
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
            formula.setDependentCells(dependencies);
            try {
                parser.checkCircularDependencies(cell,dependencies);
            } catch (CircularDependencyException e) {
                cell.setContent(previousContent);
                throw new CircularDependencyException(e.getMessage());
            }

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
                if(dependentCell == null) {
                    Content content = new NumericalContent("0");
                    dependentCell.setContent( content);
                }
                dependentCell.addCellReference(cell);
            }
        }

        LinkedList<Cell> references = new LinkedList<>();
        references.addAll(cell.getCellReferences());

        while (!references.isEmpty()) {
            Cell cellToUpdate = references.poll();
            references.addAll(cellToUpdate.getCellReferences());
            this.updateCellValue(cellToUpdate);
        }
    }

    @Override
    public double getCellContentAsDouble(String coord) throws BadCoordinateException, NoNumberException {
        // Get coordinate from cell ID
        Coordinate cellCord = new Coordinate(coord);

        // Get cell from coordinate
        Cell cell = spreadsheet.getCell(cellCord);

        // If cell is not in map create a new one
        if (cell == null) {
            Content content = new NumericalContent("0");
            spreadsheet.updateContent(cellCord, content);
            cell = spreadsheet.getCell(cellCord);
        }
        double value = cell.getDoubleValue();
        return value;
    }

    @Override
    public String getCellContentAsString(String coord) throws BadCoordinateException {
        // Get coordinate from cell ID
        Coordinate cellCord = new Coordinate(coord);

        // Get cell from coordinate
        Cell cell = spreadsheet.getCell(cellCord);

        if(cell == null) {
            // If cell is empty return empty string
            return "";
        } else {
            // Get content
            return spreadsheet.getCell(cellCord).getContent().getContent();
        }
    }

    @Override
    public String getCellFormulaExpression(String coord) throws BadCoordinateException {
        // Get coordiante from cell ID
        Coordinate cellCord = new Coordinate(coord);

        // Get content from cell
        Cell cell = spreadsheet.getCell(cellCord);

        // If cell is empty throw exception
        if(cell == null) {
            throw new BadCoordinateException("The cell is not a formula");
        }

        // Get content from cell
        Content content = cell.getContent();
        if(content instanceof FormulaContent) {
            // If cell is formula get expression without equal sign
            return content.getContent().substring(1);
        } else {
            // Otherwise throw exception
            throw new BadCoordinateException("The cell is not a formula");
        }

    }

    private void updateCellValue(Cell cellToModify) throws EvaluationException, CircularDependencyException {
        Content content = cellToModify.getContent();

        if (content instanceof FormulaContent) {
            FormulaContent formula = (FormulaContent) content;
            parser.checkCircularDependencies(cellToModify,formula.getDependentCells());
            LinkedList<Tokenizer.Token> postfixExpression = formula.getPostfixExpression();
            formulaComponentFabricator.setSpreadsheet(spreadsheet);
            LinkedList<FormulaComponent> formulaCompExpression = formulaComponentFabricator.fabricateComponentList(postfixExpression);

            double result = postfixEvaluator.evaluatePostfix(formulaCompExpression);
            content.setValue(new NumericalValue(result));
        }
    }
}


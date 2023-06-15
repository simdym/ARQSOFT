package Spreadsheet;
import Spreadsheet.Cmd.Cmd;
import Spreadsheet.Exceptions.EvaluationException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import Spreadsheet.Cmd.CmdFactory;
import Spreadsheet.Exceptions.ParserException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class Controller {
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
    private void modifyCellContent(String cellId, String newValue) {
        Coordinate cellCoor = new Coordinate(cellId);
        Cell oldcell = spreadsheet.getCell(cellCoor);
        Content previousContent;
        if (oldcell != null) {
            previousContent = oldcell.getContent();
        } else {
            previousContent = null;// Exit the method or do appropriate error handling
        }
        Content newContent = ContentFactory.createContent(newValue);
        spreadsheet.updateContent(cellCoor, newContent);
        Cell cell= spreadsheet.getCell(cellCoor);
        Content currentContent = cell.getContent();

        if (currentContent != null && (currentContent instanceof FormulaContent)) {

            FormulaContent formula = (FormulaContent) currentContent;
            try {
                tokenizer.tokenize(formula.getContent());
            } catch (Tokenizer.ParserException e) {
                formula.setValue(new TextValue("SYNTAX ERROR"));
                throw new ParserException(e.getMessage());
            }
            LinkedList<Tokenizer.Token> tokens = tokenizer.getTokens();

            try {
                parser.setTokens(tokens);
                parser.parse();
            } catch (ParserException e) {
                formula.setValue(new TextValue("SYNTAX ERROR"));
                throw new ParserException(e.getMessage());
            }
            LinkedList<Tokenizer.Token> parsedTokens = parser.getParsedTokens();

            //List<Cell> dependencies = parser.getDependencies();
            /*
            try {
                this.checkCircularDependency(coordinate, dependencies);
            } catch (CircularDependencyException e) {
                cell.setContent(previousContent);
                throw new CircularDependencyException(e.getMessage());
            }

            formula.setDependentCells(dependencies);*/


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



        if (previousContent != null) {
            if (previousContent instanceof FormulaContent) {
                FormulaContent previousFormula = (FormulaContent) previousContent;
                List<Cell> previousDependencies = previousFormula.getDependentCells();

                for (Cell dependentCell : previousDependencies) {
                    //dependentCell.removeCellReference(cell);
                }
            }
        }

        if (currentContent instanceof FormulaContent) {
            FormulaContent currentFormula = (FormulaContent) currentContent;
            List<Cell> currentDependencies = currentFormula.getDependentCells();

            for (Cell dependentCell : currentDependencies) {
               // dependentCell.addCellReference(cell);
            }
        }
        /*
        LinkedList<Cell> references = new LinkedList<Cell>();
        references.addAll(cell.getCellReferences());

        while (!references.isEmpty()) {
            Cell cellToUpdate = references.poll();
            references.addAll(cellToUpdate.getCellReferences());
            this.updateCellValue(cellToUpdate);
        }*/

    }

    }

    private void updateCellValue(Cell cellToModify) {
        Content content = cellToModify.getContent();
        if (content != null && content instanceof FormulaContent) {
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
    private void deleteContent(String cellId){}
    //public Content retrieveCellContent(Coordinate coordinate){}




}


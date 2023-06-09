package edu.upc.etsetb.arqsoft.spreadsheet_project.Formula;
import edu.upc.etsetb.arqsoft.spreadsheet.entities.CircularDependencyException;
import edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet.*;
import edu.upc.etsetb.arqsoft.spreadsheet_project.Exceptions.ParserException;
import edu.upc.etsetb.arqsoft.spreadsheet_project.Formula.Tokenizer.Token;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Parser class which checks the grammar of the formula and throws an exception if it is not correct.
 *
 * Has method to get cell dependencies
 *
 * Has method to check circular dependencies
 *
 */
public class Parser {

        private ArrayList<Integer> savedTokens;
        private LinkedList<Token> tokens;
        private Spreadsheet spreadsheet;
        public Parser() {
            savedTokens = new ArrayList<>();
            //this.spreadsheet = spreadsheet;
        }

        public void setTokens(LinkedList<Token> tokens) {
            this.tokens = tokens;
        }
        public LinkedList<Token> getParsedTokens() {
            return this.tokens;
        }

        public void parse() throws ParserException {
            checkBalancedParenthesis(tokens);
            savedTokens.clear();
            if (tokens.get(0).getTokenType() == Tokenizer.MULTDIV || tokens.get(0).getTokenType() == Tokenizer.PLUSMINUS || tokens.get(0).getTokenType() == Tokenizer.CLOSE_BRACKET) {
                throw new ParserException(
                        "Illegal grammar: a formula cannot begin with an operator or a right parenthesis");
            }

            for (Token tok : tokens) {
                int num = tok.getTokenType();

                if (num == Tokenizer.CLOSE_BRACKET) {
                    if (!savedTokens.contains(Tokenizer.OPEN_BRACKET) && !savedTokens.contains(Tokenizer.FUNCTION)) {
                        throw new ParserException(
                                "IllegalGrammar: unmatched parenthesis");
                    } else if ((!savedTokens.isEmpty()) && ((savedTokens.get(savedTokens.size() - 1) == Tokenizer.MULTDIV) || (savedTokens.get(savedTokens.size() - 1) == Tokenizer.PLUSMINUS))) {
                        throw new ParserException(
                                "IllegalGrammar: an operator cannot be followed by a closing parenthesis");
                    } else if ((!savedTokens.isEmpty()) && ((savedTokens.get(savedTokens.size() - 1) == Tokenizer.SEMICOLON))) {
                        throw new ParserException(
                                "IllegalGrammar: a semicolon must be followed by another argument");
                    } else if (savedTokens.contains(Tokenizer.FUNCTION)) {
                        ArrayList<Token> functionArguments = new ArrayList<>();

                    }
                } else if (num == Tokenizer.FUNCTION || num == Tokenizer.OPEN_BRACKET) {
                        if ((!savedTokens.isEmpty()) && ((savedTokens.get(savedTokens.size() - 1) == Tokenizer.NUMBER) || (savedTokens.get(savedTokens.size() - 1) == Tokenizer.RANGE || (savedTokens.get(savedTokens.size() - 1) == Tokenizer.CELL)))) {
                            throw new ParserException(

                                    "IllegalGrammar: Two operands or an operand and parenthesis can't be directly followed by each other ");
                        }
                    } else {
                        if (num == Tokenizer.PLUSMINUS || num == Tokenizer.MULTDIV) {
                            if ((!savedTokens.isEmpty()) && ((savedTokens.get(savedTokens.size() - 1) == Tokenizer.FUNCTION) || (savedTokens.get(savedTokens.size() - 1) == Tokenizer.RANGE || (savedTokens.get(savedTokens.size() - 1) == Tokenizer.SEMICOLON)))) {
                                throw new ParserException(

                                        "IllegalGrammar: No operators are allowed inside a function ");
                            }

                        } else if (num == Tokenizer.SEMICOLON) {
                            if ((!savedTokens.isEmpty()) && ((savedTokens.get(savedTokens.size() - 1) == Tokenizer.MULTDIV) || (savedTokens.get(savedTokens.size() - 1) == Tokenizer.PLUSMINUS))) {
                                throw new ParserException(
                                        "IllegalGrammar: A semicolon must separate cells, ranges or numbers ");
                            }
                        }
                        else if (num == Tokenizer.CELL || num == Tokenizer.RANGE || num == Tokenizer.NUMBER) {
                            if ((!savedTokens.isEmpty()) && ((savedTokens.get(savedTokens.size() - 1) == Tokenizer.CELL) || (savedTokens.get(savedTokens.size() - 1) == Tokenizer.RANGE || (savedTokens.get(savedTokens.size() - 1) == Tokenizer.NUMBER )))) {
                                throw new ParserException(
                                        "IllegalGrammar: Range, Cell and Numbers must be separated by operators or semicolons");
                            }
                        }
                        if ((!savedTokens.isEmpty()) && (savedTokens.get(savedTokens.size() - 1) == num)) { // ...repeated tokens are not allowed
                            throw new ParserException(
                                    "IllegalGrammar: repeated tokens");
                        } else if (num == Tokenizer.CELL) {
                            // check that the cells exist; if they don't, raise an exception
                            String coordinates = tok.getTokenString();
                        } else if (num == Tokenizer.RANGE) {
                            String range = tok.getTokenString();
                            Coordinate coord1 = new Coordinate(range.split(":")[0]);
                            Coordinate coord2 = new Coordinate(range.split(":")[1]);

                            if (coord1.getCol() > coord2.getCol() || coord1.getRow() > coord2.getRow()) {
                                throw new ParserException("Invalid coordinates: Invalid order of range");
                            }


                        }
                    }
                    savedTokens.add(num); // save token
                }
            }
        public void checkBalancedParenthesis(LinkedList<Token> tokens) throws ParserException {
            int openBrackets = 0;
            for (Token tok : tokens) {
                int num = tok.getTokenType();
                if (num == Tokenizer.OPEN_BRACKET || num == Tokenizer.FUNCTION ){
                    openBrackets++;
                }
                if (num == Tokenizer.CLOSE_BRACKET ){
                    openBrackets--;
                }
                if (openBrackets<0){
                    throw new ParserException("Illegal parenthesis");
                }

            }
            if (openBrackets!=0){throw new ParserException("Illegal parenthesis: unclosed parenthesis");}


    }
    public void setSpreadsheet(Spreadsheet spreadsheet){
        this.spreadsheet = spreadsheet;
    }
    public List<Cell> getCellDependencies() {
        List<Cell> dependencies = new ArrayList<>();
        for (Token tok : tokens) {
            int num = tok.getTokenType();
            if (num == Tokenizer.CELL) {
                String coordinates = tok.getTokenString();
                Coordinate coord = new Coordinate(coordinates);
                Cell cell = spreadsheet.getCell(coord);
                if (cell == null) {
                    Content content = new NumericalContent("");
                    spreadsheet.updateContent(coord, content);
                    cell = spreadsheet.getCell(coord);
                }
                dependencies.add(cell);
                collectDependencies(cell, dependencies);
            } else if (num == Tokenizer.RANGE) {
                String coordinateRange = tok.getTokenString();
                Coordinate coord1 = new Coordinate(coordinateRange.split(":")[0]);
                Coordinate coord2 = new Coordinate(coordinateRange.split(":")[1]);
                CellRange range = new CellRange(coord1, coord2, spreadsheet);
                LinkedList<Cell> cells = range.listOfCells();
                for (Cell cell : cells) {
                    dependencies.add(cell);
                    collectDependencies(cell, dependencies);
                }
            }
        }
        return dependencies;
    }

    private void collectDependencies(Cell cell, List<Cell> dependencies) { //collect dependencies
        if (cell != null && cell.getContent() instanceof FormulaContent) {
            FormulaContent formula = (FormulaContent) cell.getContent();
            List<Cell> dependentCells = formula.getDependentCells();
            for (Cell dependentCell : dependentCells) {
                if (!dependencies.contains(dependentCell)) {
                    dependencies.add(dependentCell);
                    collectDependencies(dependentCell, dependencies);
                }
            }
        }
    }

    public void checkCircularDependencies(Cell cell, List<Cell> visitedCells) throws CircularDependencyException {
        if (visitedCells.contains(cell)) { //if the cell we are checking is in the list of visitedCells, throwException
            throw new CircularDependencyException("Circular dependency");
        }

    }
}






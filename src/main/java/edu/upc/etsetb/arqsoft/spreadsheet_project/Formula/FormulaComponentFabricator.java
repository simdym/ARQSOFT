package edu.upc.etsetb.arqsoft.spreadsheet_project.Formula;
import edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet.Cell;
import edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet.Coordinate;
import edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet.NumericalValue;
import edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet.Spreadsheet;

import java.util.LinkedList;

/**
 *
 * Pure fabrication class. It is used to fabricate the components of a formula, given a list of tokens.
 *
 */
public class FormulaComponentFabricator {
    private Spreadsheet spreadsheet;
    private int i;
    public FormulaComponentFabricator(){}
    public void setSpreadsheet(Spreadsheet spreadsheet){
        this.spreadsheet = spreadsheet;
    }

    public LinkedList<FormulaComponent> fabricateComponentList(LinkedList<Tokenizer.Token> tokenList) {

        LinkedList<FormulaComponent> componentList = new LinkedList<>();
        LinkedList<Tokenizer.Token> functionArgumentTokens = new LinkedList<>();
        int openFunction = 0;
        for (int i = 0; i < tokenList.size(); i++) { //Iterate over list of tokens
            Tokenizer.Token token = tokenList.get(i);
            int tokenType = token.getTokenType();
            if (tokenType == 4 || tokenType == 5) { //If operator, add directly to component list
                Operator operator = new Operator(token);
                componentList.add(operator);
            }
            //following are Opperands:
            else if(tokenType==6){ //If number, add directly to component list
                NumericalValue value = new NumericalValue(token.getTokenString());
                componentList.add(value);
            }
            else if(tokenType==8){ //If cell, add directly to component list
                Coordinate coord = new Coordinate(token.getTokenString());
                Cell cell = spreadsheet.getCell(coord);
                componentList.add(cell);
            }
             else if (tokenType == 1) { //If function, add function to component list and add its arguments to a list of tokens
                int j = findMatchingClosingParenthesis(tokenList, i); //find the closing parenthesis of the function
                LinkedList<Tokenizer.Token> nestedTokens = new LinkedList<>(tokenList.subList(i, j));//get the tokens between the parenthesis
                Function function = functionGenerator(nestedTokens, spreadsheet, i);//generate the function
                componentList.add(function);//add function to component list

                i=j;//update i to the index of the closing parenthesis
             }
        }
        return componentList;
    }


    //Generates a funtion. Gets a list of tokens and adds them as arguments to the function. If the token is another
    //function, it calls itself recursively to generate the function and its arguments.
    public static Function functionGenerator(LinkedList<Tokenizer.Token> tokenList,Spreadsheet spreadsheet, int ii) {
        Tokenizer.Token functionToken = tokenList.getFirst();
        Function function = Function.FunctionFactory.createFunction(functionToken.getTokenString());
        ii=1;
        while (ii < tokenList.size()) {
            Tokenizer.Token token = tokenList.get(ii);
            if (token.getTokenType() == 6) { // if number, add directly to arguments
                Argument arg = new NumericalValue(token.getTokenString());
                function.addArgument(arg);

            } else if (token.getTokenType() == 7) { // if range, add directly to arguments
                String[] coordString = token.getTokenString().split(":");
                Coordinate coord1 = new Coordinate(coordString[0]);
                Coordinate coord2 = new Coordinate(coordString[1]);
                CellRange cellRange = new CellRange(coord1, coord2, spreadsheet);
                for (Cell cell : cellRange.listOfCells()) {
                    function.addArgument(cell);

                }
            } else if (token.getTokenType() == 8) { // if cell, add directly to arguments
                Coordinate coord = new Coordinate(token.getTokenString());
                Argument arg = spreadsheet.getCell(coord);
                function.addArgument(arg);

            } else if (token.getTokenType() == 1) { // if another function
                int nestedFunctionEndIndex = findMatchingClosingParenthesis(tokenList, ii);
                LinkedList<Tokenizer.Token> nestedTokens = new LinkedList<>(tokenList.subList(ii, nestedFunctionEndIndex));
                Argument arg = functionGenerator(nestedTokens, spreadsheet, ii);//recursive call to generate another function which is then saved as an argumenr
                function.addArgument(arg);
                ii = nestedFunctionEndIndex;
            }
            ii++;
        }
        return function;
    }
    public static int findMatchingClosingParenthesis(LinkedList<Tokenizer.Token> tokenList, int i) {
        int nests = 0;
        int j = i;

        while (j < tokenList.size()) {
            Tokenizer.Token token = tokenList.get(j);
            if (token.getTokenType() == 1) {
                nests++; //if opening parenthesis, increase nest counter
            } else if (token.getTokenType() == 3) {
                nests--;// if closing parenthesis, decrease nest counter (closing function)
                if (nests == 0) {
                    break;
                }
            }
            j++;
        }
        if (nests > 0) {
            throw new IllegalArgumentException("Unbalanced parentheses");
        }
        return j;
    }




}

package Spreadsheet;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Operand extends FormulaComponent {
    private Spreadsheet spreadsheet;
    private LinkedList<Tokenizer.Token> operandTokens;
    public Operand(LinkedList<Tokenizer.Token> operandTokens){
        super(operandTokens);
        this.operandTokens = operandTokens;
    }
    public double getValue(Spreadsheet spreadsheet){
        Tokenizer.Token token = operandTokens.getFirst();
        double value=0;
        if (token.getTokenType()==6){//if number
            value = new NumericalValue(token.getTokenString()).getDoubleValue();

        }
        else if (token.getTokenType()==8) {//if cells
            Coordinate coord = new Coordinate(token.getTokenString());
            Cell cell = spreadsheet.getCell(coord);
            value = cell.getDoubleValue();

        }
        else if (token.getTokenType() == 1){ //Then it's function
            //for (Tokenizer.Token tok : operandTokens) {System.out.println("" + tok.token + " " + tok.getTokenString());}
            Function function = functionGenerator(operandTokens,spreadsheet);
            value = function.getDoubleValue();
        }

        return value;
    }

    public Function functionGenerator(LinkedList<Tokenizer.Token> tokenList, Spreadsheet spreadsheet) {
        Tokenizer.Token functionToken = tokenList.getFirst();
        Function function = Function.FunctionFactory.createFunction(functionToken.getTokenString());
        int i = 1;
        while (i < tokenList.size()) {
            Tokenizer.Token token = tokenList.get(i);
            if (token.getTokenType() == 6) { // if number
                Argument arg = new NumericalValue(token.getTokenString());
                function.addArgument(arg);
            } else if (token.getTokenType() == 7) { // if range
                String[] coordString = token.getTokenString().split(":");
                Coordinate coord1 = new Coordinate(coordString[0]);
                Coordinate coord2 = new Coordinate(coordString[1]);
                CellRange cellRange = new CellRange(coord1, coord2, spreadsheet);
                for (Cell cell : cellRange.listOfCells()) {
                    function.addArgument(cell);
                }
            } else if (token.getTokenType() == 8) { // if cell
                Coordinate coord = new Coordinate(token.getTokenString());
                Argument arg = spreadsheet.getCell(coord);
                function.addArgument(arg);
            } else if (token.getTokenType() == 1) { // if another function
                int nestedFunctionEndIndex = findMatchingClosingParenthesis(tokenList, i);
                LinkedList<Tokenizer.Token> nestedTokens = new LinkedList<>(tokenList.subList(i, nestedFunctionEndIndex));
                Argument arg = functionGenerator(nestedTokens, spreadsheet);
                function.addArgument(arg);
                i = nestedFunctionEndIndex;
            }
            i++;
        }
        return function;
    }


    public int findMatchingClosingParenthesis(LinkedList<Tokenizer.Token> tokenList, int startIndex) {
        int nests = 0;
        int j = startIndex;
        while (j < tokenList.size()) {
            Tokenizer.Token token = tokenList.get(j);
            if (token.getTokenType() == 1) {
                nests++;
            } else if (token.getTokenType() == 3) {
                nests--;
                if (nests == 0) {
                    break;
                }
            }
            j++;
        }

        if (nests != 0) {
            throw new IllegalArgumentException("Unbalanced parentheses");
        }

        return j;
    }


}

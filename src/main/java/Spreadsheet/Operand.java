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
        if (token.token==6){//if number
            value = new NumericalValue(token.sequence).getDoubleValue();

        }
        if (token.token==8) {//if cells
            Coordinate coord = new Coordinate(token.sequence);
           /*
            Content  content=  cell.getContent();
            Object valueObj=  content.getValue().getValue();*/
            Cell cell = spreadsheet.getCell(coord);
            value = cell.getDoubleValue();

        }
        else if (token.token == 1){ //Then it's function
            Function function = functionGenerator(operandTokens,spreadsheet);
            value = function.getDoubleValue();
        }

        return value;
    }

    public Function functionGenerator(LinkedList<Tokenizer.Token> tokenList, Spreadsheet spreadsheet){
        Tokenizer.Token functionToken= tokenList.getFirst();
        Function function = Function.FunctionFactory.createFunction(functionToken.sequence);
        for (int i = 1; i < tokenList.size(); i++) {
            Tokenizer.Token token = tokenList.get(i);
            if (token.token==6){//if number
                Argument arg = new NumericalValue(token.sequence);
                function.addArgument(arg);
            }
            if (token.token==7){//if range
                String[] coordString = token.sequence.split(":");
                Coordinate coord1 = new Coordinate(coordString [0]);
                Coordinate coord2 = new Coordinate(coordString [1]);
                CellRange cellRange = new CellRange(coord1,coord2,spreadsheet);
                for (Cell cell : cellRange.listOfCells()){
                    function.addArgument(cell);
                }

            }
            if (token.token==8){// if cell
                Coordinate coord = new Coordinate(token.sequence);
                Argument arg = spreadsheet.getCell(coord);
                function.addArgument(arg);
            }
            if (token.token==1){// if another function
                int nestedFunctionEndIndex = findMatchingClosingParenthesis(tokenList, i);
                LinkedList<Tokenizer.Token> nestedTokens= new LinkedList<>(tokenList.subList(i + 1, nestedFunctionEndIndex));
                Argument arg=functionGenerator(nestedTokens, spreadsheet);
                function.addArgument(arg);
                i = nestedFunctionEndIndex;

            }

        }
        return function;
    }

    public int findMatchingClosingParenthesis(LinkedList<Tokenizer.Token> tokenList, int startIndex) {
        int nests = 1;
        int j = startIndex;
        while (nests != 0 && j < tokenList.size()) {
            Tokenizer.Token token = tokenList.get(j);

            if (token.token == 1) {
                nests += 1;
            } else if (token.token == 3) {
                nests -= 1;
            }
            j++;
        }

        if (nests != 0) {
            throw new IllegalArgumentException("Unbalanced parentheses");
        }

        return j ;
    }




}

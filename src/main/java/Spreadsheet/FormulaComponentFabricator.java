package Spreadsheet;
import java.text.Normalizer;
import java.util.LinkedList;

public class FormulaComponentFabricator {
    private Spreadsheet spreadsheet;
    private int i;
    public FormulaComponentFabricator(){}

    public static LinkedList<FormulaComponent> fabricateComponentList(LinkedList<Tokenizer.Token> tokenList, Spreadsheet spreadsheet) {
        LinkedList<FormulaComponent> componentList = new LinkedList<>();
        LinkedList<Tokenizer.Token> functionArgumentTokens = new LinkedList<>();
        int openFunction = 0;
        for (int i = 0; i < tokenList.size(); i++) {
            Tokenizer.Token token = tokenList.get(i);
            int tokenType = token.getTokenType();
            if (tokenType == 4 || tokenType == 5) {
                Operator operator = new Operator(token);
                componentList.add(operator);
            }
            //following are Opperands:
            else if(tokenType==6){
                NumericalValue value = new NumericalValue(token.getTokenString());
                componentList.add(value);
            }
            else if(tokenType==8){
                Coordinate coord = new Coordinate(token.getTokenString());
                Cell cell = spreadsheet.getCell(coord);
                componentList.add(cell);
            }
             else if (tokenType == 1) {
                int j = findMatchingClosingParenthesis(tokenList, i);
                LinkedList<Tokenizer.Token> nestedTokens = new LinkedList<>(tokenList.subList(i, j));
                Function function = functionGenerator(nestedTokens, spreadsheet, i);
                componentList.add(function);

                i=j;
             }
        }
        return componentList;
    }

    public static Function functionGenerator(LinkedList<Tokenizer.Token> tokenList,Spreadsheet spreadsheet, int ii) {
        Tokenizer.Token functionToken = tokenList.getFirst();
        Function function = Function.FunctionFactory.createFunction(functionToken.getTokenString());
        ii=1;
        while (ii < tokenList.size()) {
            Tokenizer.Token token = tokenList.get(ii);
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
                int nestedFunctionEndIndex = findMatchingClosingParenthesis(tokenList, ii);
                LinkedList<Tokenizer.Token> nestedTokens = new LinkedList<>(tokenList.subList(ii, nestedFunctionEndIndex));
                Argument arg = functionGenerator(nestedTokens, spreadsheet, ii);
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
                nests++;
            } else if (token.getTokenType() == 3) {
                nests--;
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

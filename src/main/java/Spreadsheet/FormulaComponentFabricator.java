package Spreadsheet;
import java.text.Normalizer;
import java.util.LinkedList;

public class FormulaComponentFabricator {

    public FormulaComponentFabricator(){}

    public LinkedList<FormulaComponent> fabricateComponentList(LinkedList<Tokenizer.Token> tokenList) {
        LinkedList<FormulaComponent> componentList = new LinkedList<>();
        LinkedList<Tokenizer.Token> functionArgumentTokens = new LinkedList<>();
        int openFunction = 0;
        for (Tokenizer.Token token : tokenList) {
            LinkedList<Tokenizer.Token> operandTokens = new LinkedList<>();
            int tokenType = token.token;

            if (tokenType == 4 || tokenType == 5) {
                Operator operator = new Operator(token);
                componentList.add(operator);
            } else if (tokenType == 6 || tokenType == 7) {
                if (openFunction > 0) {
                    functionArgumentTokens.add(token);
                } else {
                    operandTokens.add(token);
                    Operand operand = new Operand(operandTokens);
                    componentList.add(operand);
                }
            } else if (tokenType == 1) {
                // Start of a function, save the tokens until the closing parenthesis is found
                functionArgumentTokens.add(token);
                openFunction += 1;
            } else if (tokenType == 10) {
                // Semicolon, adds it to list to create operand
                functionArgumentTokens.add(token);
            } else if (tokenType == 3) { // end of function
                functionArgumentTokens.add(token);
                openFunction -= 1;
                if(openFunction==0) {
                    Operand functionOperand = new Operand(functionArgumentTokens);
                    componentList.add(functionOperand);
                    functionArgumentTokens.clear();}
            } else {
                throw new IllegalArgumentException("Invalid token type: " + tokenType);
            }
        }

        System.out.println("Length of componentList: " + componentList.size());
        return componentList;
    }


}

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
            int tokenType = token.token;

            if (tokenType == 4 || tokenType == 5) {
                Operator operator = new Operator(token);
                componentList.add(operator);
            } else if (tokenType == 6 ||  tokenType == 7||tokenType == 8) {
                if (openFunction > 0) {
                    functionArgumentTokens.add(token);
                } else {
                    LinkedList<Tokenizer.Token> operandTokens = new LinkedList<>();
                    operandTokens.add(token);
                    Operand operand = new Operand(operandTokens);
                    componentList.add(operand);
                }
            } else if (tokenType == 1) {
                if (openFunction > 0) {
                    functionArgumentTokens.add(token);
                    openFunction++;
                } else {
                    functionArgumentTokens = new LinkedList<>();
                    functionArgumentTokens.add(token);
                    openFunction++;
                }
            } else if (tokenType == 10) {
                functionArgumentTokens.add(token);
            } else if (tokenType == 3) { // end of function
                functionArgumentTokens.add(token);

                openFunction -= 1;
                if (openFunction == 0) {
                    //for (Tokenizer.Token tok : functionArgumentTokens) {System.out.println("" + tok.token + " " + tok.sequence);}
                    Operand functionOperand = new Operand(functionArgumentTokens);
                    componentList.add(functionOperand);
                }
            } else {
                throw new IllegalArgumentException("Invalid token type: " + tokenType);
            }
        }

        return componentList;
    }



}

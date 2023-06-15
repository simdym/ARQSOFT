package Spreadsheet;
import java.util.LinkedList;
import java.util.Collections;
public class Operator implements FormulaComponent{
    private Tokenizer.Token operatorToken;
    public Operator(Tokenizer.Token operatorToken){
        super();
        this.operatorToken = operatorToken;
    }

    public double evaluateOperation(double operand1, double operand2) {
        double result = 0;
        switch (operatorToken.getTokenString()) {
            case "+":
                result = operand1 + operand2;
                break;
            case "-":
                result = operand1 - operand2;
                break;
            case "*":
                result = operand1 * operand2;
                break;
            case "/":
                result = operand1 / operand2;
                break;
        }
        return result;
    }
}

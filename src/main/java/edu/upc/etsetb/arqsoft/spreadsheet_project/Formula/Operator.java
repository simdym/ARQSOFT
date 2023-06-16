package edu.upc.etsetb.arqsoft.spreadsheet_project.Formula;

import edu.upc.etsetb.arqsoft.spreadsheet_project.Exceptions.EvaluationException;

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
                if(operand2 == 0) {
                    throw new EvaluationException("Dividing by zero-error");
                }
                result = operand1 / operand2;
                break;
        }
        return result;
    }
}

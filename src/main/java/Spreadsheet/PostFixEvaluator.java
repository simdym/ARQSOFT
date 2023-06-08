package Spreadsheet;
import java.util.Stack;
import java.util.LinkedList;
import Spreadsheet.Tokenizer;

public class PostFixEvaluator {
    public PostFixEvaluator(){}

    public float  evaluatePostfix(LinkedList<Tokenizer.Token> postFixExpression){
        Stack<Float> operationStack = new Stack<>();
        float result ;

        for (Tokenizer.Token token : postFixExpression) {
            if (token.token == 6 ) { // if number, add to stack
                Float floatToken =  Float.parseFloat(token.sequence);
                operationStack.push(floatToken);
            }
            if (token.token == 8) { // if cell, first get value then add to stack
                //getvalue
                Float floatToken =  Float.parseFloat(token.sequence);
                operationStack.push(floatToken);
            }

            else if (token.token == 4 || token.token == 5) { // if number or cell, add to stack
                float tk2 = operationStack.pop();
                float tk1 = operationStack.pop();
                result = evaluateOperation(tk1,tk2,token);
                operationStack.push(result);
                }
            }
        result = operationStack.pop();
        if (!operationStack.empty()){}//throw exception not empty stack
        return result;
    }


    private float evaluateOperation(Float tk1, Float tk2, Tokenizer.Token operator) {
        float result = 0;
        switch (operator.sequence) {
            case "+":
                result =tk1 + tk2;
                break;
            case "-":
                result = tk1 - tk2;
                break;
            case "*":
                result = tk1 * tk2;
                break;
            case "/":
                result = tk1 / tk2;
                break;
        }
        return result;
    }
}


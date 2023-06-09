package Spreadsheet;
import java.util.Stack;
import java.util.LinkedList;
import Spreadsheet.Tokenizer;
import java.util.Collections;
public class PostFixEvaluator {
    public PostFixEvaluator(){}

    public float evaluatePostfix(LinkedList<Tokenizer.Token> postFixExpression) {
        Stack<Float> operationStack = new Stack<>();
        float result;

        for (Tokenizer.Token token : postFixExpression) {
            if (token.token == 6) { // if number, add to stack
                Float floatToken = Float.parseFloat(token.sequence);
                operationStack.push(floatToken);
            } else if (token.token == 8) { // if cell, first get value then add to stack
                Float floatToken = Float.parseFloat(token.sequence);
                operationStack.push(floatToken);
            } else if (token.token == 1) { // if function token
                LinkedList<Float> stackedNum = new LinkedList<>();
                while (!operationStack.isEmpty()) {
                    stackedNum.add(operationStack.pop());
                }
                Collections.sort(stackedNum);
                float sum = 0;
                int size = stackedNum.size();
                switch (token.sequence) {
                    case "MIN":
                        operationStack.push(Collections.min(stackedNum));
                        break;
                    case "MAX":
                        operationStack.push(Collections.max(stackedNum));
                        break;
                    case "SUM":
                        for (int i = 0; i < size; i++) {
                            sum += stackedNum.get(i);
                        }
                        operationStack.push(sum);
                        break;
                    case "AVG":
                        for (int i = 0; i < size; i++) {
                            sum += stackedNum.get(i);
                        }
                        operationStack.push(sum / size);
                        break;
                    default:
                        break;
                }
            } else if (token.token == 4 || token.token == 5) { // if operator
                float tk2 = operationStack.pop();
                float tk1 = operationStack.pop();
                result = evaluateOperation(tk1, tk2, token);
                operationStack.push(result);
            }
        }

        result = operationStack.pop();
        if (!operationStack.empty()) {
            // throw exception for non-empty stack
        }
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


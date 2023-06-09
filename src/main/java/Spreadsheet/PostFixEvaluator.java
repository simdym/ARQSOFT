package Spreadsheet;
import java.util.Stack;
import java.util.LinkedList;
import Spreadsheet.Tokenizer;
import java.util.Collections;


public class PostFixEvaluator {
    public PostFixEvaluator() {}

    public float evaluatePostfix(LinkedList<Tokenizer.Token> postFixExpression) {
        Stack<Float> operandStack = new Stack<>();

        for (Tokenizer.Token token : postFixExpression) {
            int num = token.token;
            String str = token.sequence;

            if (num == 6 || num == 8 || num == 7) { // if read token is a number or operand
                float value = Float.parseFloat(str);
                operandStack.push(value); // push it onto the operand stack
            } else if (num == 1) { // if read token is a function
                // Evaluate the function
                LinkedList<Float> stackedNum = new LinkedList<>();
                LinkedList<Tokenizer.Token> functionArgs = token.functionArgs;
                while (!operandStack.isEmpty() && !functionArgs.isEmpty()) {
                    stackedNum.addFirst(operandStack.pop());
                }
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
                        break;/ Default value if function evaluation fails

                LinkedList<Float> stackedNum
                operandStack.push(result); // push the result onto the operand stack
            } else if (num == 4 || num == 5) { // if read token is an operator
                float operand2 = operandStack.pop();
                float operand1 = operandStack.pop();
                float result = evaluateOperation(operand1, operand2, token);
                operandStack.push(result); // push the result onto the operand stack
            }
        }

        float finalResult = operandStack.pop();
        if (!operandStack.isEmpty()) {
            // throw exception for non-empty stack
        }
        return finalResult;
    }

    private float evaluateOperation(float operand1, float operand2, Tokenizer.Token operator) {
        float result = 0;
        switch (operator.sequence) {
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

package Spreadsheet;
import java.util.Stack;
import java.util.LinkedList;
import Spreadsheet.Tokenizer;
import java.util.Collections;


public class PostFixEvaluator {
    public PostFixEvaluator() {}

    public float evaluatePostfix(LinkedList<Tokenizer.Token> postFixExpression) {
            boolean skipNext = false;
            float result;
            Stack<Float> operandStack = new Stack<>();
            LinkedList<Float> args = new LinkedList<Float>();

            for (Tokenizer.Token token : postFixExpression) {
                int num = token.token;
                String str = token.sequence;
                if (num == 6 || num == 8 || num == 7) { // if read token is a number or operand
                    float value = Float.parseFloat(str);
                    operandStack.push(value); // push it onto the operand stack
                }
                else if (num == 1){
                    float arg = operandStack.pop();
                    args.add(arg);
                    result = evaluateFunction(token, args);
                    System.out.println(args);
                    operandStack.push(result);
                    args.clear();
                }else if (num == 10) { // if read token is a function
                    Tokenizer.Token nextToken = postFixExpression.get(postFixExpression.indexOf(token) + 1);
                     // Get the next element's sequence as the argument
                    float arg = operandStack.pop();
                    args.add(arg);


                }
                else if (num == 4 || num == 5) { // if read token is an operator
                    float operand2 = operandStack.pop();
                    float operand1 = operandStack.pop();
                    result = evaluateOperation(operand1, operand2, token);
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

        private float evaluateFunction(Tokenizer.Token functionToken, LinkedList<Float> arguments) {
            String functionName = functionToken.sequence;
            switch (functionName) {
                case "MIN(":
                    return Collections.min(arguments);
                case "MAX(":
                    return Collections.max(arguments);
                case "SUM(":
                    float sum = 0;
                    for (float argument : arguments) {
                        sum += argument;
                    }
                    return sum;
                case "AVG(":
                    float total = 0;
                    for (float argument : arguments) {
                        total += argument;
                    }
                    return total / arguments.size();
                default:
                    // Handle other functions
                    break;
            }
            return 0; // Default value if function evaluation fails
        }
    }
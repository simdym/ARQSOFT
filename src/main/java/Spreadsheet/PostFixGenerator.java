package Spreadsheet;
import java.util.Stack;
import java.util.LinkedList;
import Spreadsheet.Tokenizer;

public class PostFixGenerator {
    public PostFixGenerator() {}

    public LinkedList<Tokenizer.Token> generatePostfix(LinkedList<Tokenizer.Token> tokenList) {
        LinkedList<Tokenizer.Token> postfixList = new LinkedList<>();
        Stack<Tokenizer.Token> operatorStack = new Stack<>();
        Stack<LinkedList<Tokenizer.Token>> functionArgStack = new Stack<>();

        for (Tokenizer.Token token : tokenList) {
            int num = token.token; // read integer-format token
            String str = token.sequence; // string character read

            if (num == 6 || num == 8 || num == 7) { // if read token is a number or operand
                postfixList.add(token); // add Token to the queue
            } else if (num == 1) { // if read token is a function
                operatorStack.push(token); // push function token onto the operator stack
                functionArgStack.push(new LinkedList<>()); // push an empty argument list onto the function argument stack
            } else if (num == 4 || num == 5) { // if read token is an operator
                while (!operatorStack.isEmpty() && operatorStack.peek().token != 2 &&
                        ((operatorStack.peek().token > num) ||
                                (operatorStack.peek().token == num && isLeftAssociative(operatorStack.peek().sequence)))) {
                    postfixList.add(operatorStack.pop());
                }
                operatorStack.push(token);
            } else if (str.equals(",")) { // if read token is a comma
                while (!operatorStack.isEmpty() && operatorStack.peek().token != 2) {
                    postfixList.add(operatorStack.pop());
                }
                Tokenizer.Token argument = postfixList.removeLast(); // remove the argument from the output queue
                LinkedList<Tokenizer.Token> currentFunctionArgList = functionArgStack.peek(); // get the current function's argument list
                currentFunctionArgList.add(argument); // append the argument to the current function's argument list
            } else if (num == 2) { // if read token is an open parenthesis
                operatorStack.push(token);
            } else if (num == 3) { // if read token is a right parenthesis
                while (!operatorStack.isEmpty() && operatorStack.peek().token != 2) {
                    postfixList.add(operatorStack.pop());
                }
                operatorStack.pop(); // discard the left parenthesis

                if (!operatorStack.isEmpty() && operatorStack.peek().token == 1) { // if there is a function token on top of the operator stack
                    postfixList.add(operatorStack.pop()); // pop the function from the operator stack into the output queue
                    LinkedList<Tokenizer.Token> argumentList = functionArgStack.pop(); // pop the argument list from the function argument stack
                    postfixList.addAll(argumentList); // append the argument list to the output queue
                }
            }
        }

        while (!operatorStack.isEmpty()) {
            postfixList.add(operatorStack.pop());
        }

        return postfixList;
    }

    private boolean isLeftAssociative(String operator) {
        return operator.equals("+") || operator.equals("-") || operator.equals("*") || operator.equals("/");
    }



}

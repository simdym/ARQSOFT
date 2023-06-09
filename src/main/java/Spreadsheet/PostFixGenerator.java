package Spreadsheet;
import java.util.Stack;
import java.util.LinkedList;
import Spreadsheet.Tokenizer;
public class PostFixGenerator {
    public PostFixGenerator() {}

    public LinkedList<Tokenizer.Token> generatePostfix(LinkedList<Tokenizer.Token> tokenList) {
        LinkedList<Tokenizer.Token> postfixList = new LinkedList<>();
        Stack<Tokenizer.Token> operatorStack = new Stack<>();

        for (Tokenizer.Token token : tokenList) {
            int num = token.token; // read integer-format token
            String str = token.sequence; // string character read

            if (num == 6 || num == 7 || num == 8) { // if read token is a number or operand
                postfixList.add(token); // add Token to the queue
            } else if (num == 1) { // if read token is a function
                operatorStack.push(token); // push function token onto the operator stack

            } else if (num == 4 || num == 5) { // if read token is an operator
                while (!operatorStack.isEmpty() && operatorStack.peek().token != 2 && operatorStack.peek().token != 10&&
                        ((operatorStack.peek().token > num) ||
                                (operatorStack.peek().token == num && isLeftAssociative(operatorStack.peek().sequence)))) {
                    postfixList.add(operatorStack.pop());
                }
                operatorStack.push(token);
            } else if (num==10) { // if read token is a comma
                //while (!operatorStack.isEmpty() && operatorStack.peek().token != 2) {
                   // postfixList.add(operatorStack.pop());
               // }
                operatorStack.push(token);
            } else if (num == 2) { // if read token is an open parenthesis
                operatorStack.push(token);
            } else if (num == 3) { // if read token is a right parenthesis
                while (!operatorStack.isEmpty() && operatorStack.peek().token != 2 && operatorStack.peek().token != 1) {
                    postfixList.add(operatorStack.pop());
                }
                if (operatorStack.peek().token == 1){postfixList.add(operatorStack.pop());}
                else {operatorStack.pop();}// discard the left parenthesis

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
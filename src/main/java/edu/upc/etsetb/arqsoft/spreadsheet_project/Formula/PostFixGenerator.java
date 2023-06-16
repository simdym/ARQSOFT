package edu.upc.etsetb.arqsoft.spreadsheet_project.Formula;
import java.util.Stack;
import java.util.LinkedList;

/**
 *
 * Class which generates a postfix list of tokens  from a list of tokens.
 */
public class PostFixGenerator {
    public PostFixGenerator() {}

    public LinkedList<Tokenizer.Token> generatePostfix(LinkedList<Tokenizer.Token> tokenList) {
        LinkedList<Tokenizer.Token> postfixList = new LinkedList<>();
        Stack<Tokenizer.Token> operatorStack = new Stack<>();
        int activefuns = 0;
        for (Tokenizer.Token token : tokenList) {
            int num = token.getTokenType(); // read integer-format token
            String str = token.getTokenString(); // string character read

            if (num == Tokenizer.NUMBER || num == Tokenizer.RANGE || num == Tokenizer.CELL) { // if read token is a number or operand
                postfixList.add(token); // add Token to the list directl
            }

            else if (num == Tokenizer.FUNCTION) { // if read token is a function
                postfixList.add(token);
                activefuns+=1;// push function token onto the operator stack and

            } else if (num == Tokenizer.PLUSMINUS || num == Tokenizer.MULTDIV) { // if read token is an operator
                while (!operatorStack.isEmpty() && operatorStack.peek().getTokenType() != 2 && operatorStack.peek().getTokenType() != 10&&
                        ((operatorStack.peek().getTokenType() > num) ||
                                (operatorStack.peek().getTokenType() == num && isLeftAssociative(operatorStack.peek().getTokenString())))) {//Check if the operator at the top of the operator stack has higher precedence
                    postfixList.add(operatorStack.pop());//pop operators from the operator stack onto the output queue until the operator at the top of the operator stack can be placed in stack
                }
                operatorStack.push(token);

            }

            else if (num == Tokenizer.SEMICOLON) { // if read token is ; add directly to string (will be a part of function)
                postfixList.add(token);}
            else if (num == Tokenizer.OPEN_BRACKET) { // if read token is an open parenthesis, push it onto the operator stack
                operatorStack.push(token);
            } else if (num == Tokenizer.CLOSE_BRACKET) { // ead token is a right parenthesis
                if (activefuns>0){postfixList.add(token);activefuns-=1;}// if a funtion is open,  rclose function
                else {
                    while (!operatorStack.isEmpty() && operatorStack.peek().getTokenType() != 2 && operatorStack.peek().getTokenType() != 1) {// pop operators from the operator stack onto the output queue until the operator at the top of the operator stack is a left parenthesis
                        postfixList.add(operatorStack.pop());
                    }
                    if (operatorStack.peek().getTokenType() == 1) { //
                        postfixList.add(operatorStack.pop());
                    } else {
                        operatorStack.pop();
                    }// discard the left parenthesis
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
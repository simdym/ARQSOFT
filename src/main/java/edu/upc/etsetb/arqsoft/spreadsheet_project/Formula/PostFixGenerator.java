package edu.upc.etsetb.arqsoft.spreadsheet_project.Formula;
import java.util.Stack;
import java.util.LinkedList;

public class PostFixGenerator {
    public PostFixGenerator() {}

    public LinkedList<Tokenizer.Token> generatePostfix(LinkedList<Tokenizer.Token> tokenList) {
        LinkedList<Tokenizer.Token> postfixList = new LinkedList<>();
        Stack<Tokenizer.Token> operatorStack = new Stack<>();
        int activefuns = 0;
        for (Tokenizer.Token token : tokenList) {
            int num = token.getTokenType(); // read integer-format token
            String str = token.getTokenString(); // string character read

            if (num == 6 || num == 7 || num == 8) { // if read token is a number or operand
                postfixList.add(token); // add Token to the queue
            }

            else if (num == 1) { // if read token is a function
                postfixList.add(token);
                activefuns+=1;// push function token onto the operator stack

            } else if (num == 4 || num == 5) { // if read token is an operator
                while (!operatorStack.isEmpty() && operatorStack.peek().getTokenType() != 2 && operatorStack.peek().getTokenType() != 10&&
                        ((operatorStack.peek().getTokenType() > num) ||
                                (operatorStack.peek().getTokenType() == num && isLeftAssociative(operatorStack.peek().getTokenString())))) {
                    postfixList.add(operatorStack.pop());
                }
                operatorStack.push(token);

            }
            else if (num == 1) { // if read token is ;
                postfixList.add(token);}
            else if (num == 10) { // if read token is ;
                postfixList.add(token);}
            else if (num == 2) { // if read token is an open parenthesis
                operatorStack.push(token);
            } else if (num == 3) {
                if (activefuns>0){postfixList.add(token);activefuns-=1;}// if read token is a right parenthesis
                else {
                    while (!operatorStack.isEmpty() && operatorStack.peek().getTokenType() != 2 && operatorStack.peek().getTokenType() != 1) {
                        postfixList.add(operatorStack.pop());
                    }
                    if (operatorStack.peek().getTokenType() == 1) {
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
package Spreadsheet;
import java.util.Stack;
import java.util.LinkedList;
import Spreadsheet.Tokenizer;
public class PostFixGenerator {
    public PostFixGenerator(){}
    public LinkedList<Tokenizer.Token> generatePostfix(LinkedList<Tokenizer.Token> tokenList) {
        LinkedList<Tokenizer.Token> postfixList = new LinkedList<>();
        Stack<Tokenizer.Token> operatorStack = new Stack<>();

        for (Tokenizer.Token token : tokenList) {
            if (token.token == 1) { // if number or cell, add directly to list
                //FORMULA QUE ET RETORNI EL NUMERO
            }
            if (token.token == 6 || token.token == 8) { // if number or cell, add directly to list
                postfixList.add(token);
            } else if (token.token == 2) { // if ( precedence 0, push on stack
                operatorStack.push(token);
            } else if (token.token == 4 || token.token == 5) { // if +, -, *, or /, handle operator precedence
                while (!operatorStack.isEmpty() && operatorStack.peek().token != 2 &&
                        getPrecedence(operatorStack.peek()) >= getPrecedence(token)) {
                    postfixList.add(operatorStack.pop());
                }
                operatorStack.push(token);
            } else if (token.token == 3) { // if ), pop until finding matching parenthesis
                while (!operatorStack.isEmpty() && operatorStack.peek().token != 2) {
                    postfixList.add(operatorStack.pop());
                }
                if (!operatorStack.isEmpty() && operatorStack.peek().token == 2) {
                    operatorStack.pop(); // Discard the left parenthesis
                } else {
                    throw new IllegalArgumentException("Mismatched parentheses");
                }
            }
        }

        while (!operatorStack.isEmpty()) {
            if (operatorStack.peek().token == 2 || operatorStack.peek().token == 3) {
                throw new IllegalArgumentException("Mismatched parentheses");
            }
            postfixList.add(operatorStack.pop());
        }

        return postfixList;
    }

    private int getPrecedence(Tokenizer.Token operator) {
        if (operator.token == 4 || operator.token == 5) {
            return 1; // + or - has the same precedence as * or /
        } else if (operator.token == 6) {
            return 2; // * or / has higher precedence
        }
        return 0; // Other operators have the lowest precedence
    }

}


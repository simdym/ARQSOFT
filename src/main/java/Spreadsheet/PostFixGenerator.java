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

            if (num == 6 || num == 8|| num ==7) { // if read token is a number...
                //postfixList += str;
                postfixList.add(token); // ...add Token to the queue
            }
            else if (num == 1) {
                operatorStack.push(token);
            }
            else if (num == 4 || num == 5) {

                while ((!operatorStack.isEmpty()) && (operatorStack.peek().token != 2) && (operatorStack.peek().token >= num)) {
                    postfixList.add(operatorStack.pop());
                }

                operatorStack.push(token);
            }

            else if (num == 2) { // if read token is an open bracket...
                operatorStack.push(token);
            }
            else if (num == 3){ // if read token IS a right parenthesis...
                while ((!operatorStack.isEmpty()) && (operatorStack.peek().token != 2)) { // the operator at the top of the operator stack is not a left parenthesis
                    postfixList.add(operatorStack.pop());
                }

                operatorStack.pop();

                if ((!operatorStack.isEmpty()) && (operatorStack.peek().token == 1)){
                    postfixList.add(operatorStack.pop());
                }
            }


        }
        while (!operatorStack.isEmpty()) {
            postfixList.add(operatorStack.pop());
        }

        return postfixList;
    }


}

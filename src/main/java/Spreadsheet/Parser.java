package Spreadsheet;
import Spreadsheet.Tokenizer.Token;
import Spreadsheet.Exceptions.*;
import java.util.LinkedList;
public class Parser {
    public Parser(){}
    public LinkedList<Token> parse(LinkedList<Token> tokenList){

        return tokenList;
    }

    public class ArithmeticSyntaxError extends RuntimeException {
        public ArithmeticSyntaxError(String message) {
            super(message);
        }

}}

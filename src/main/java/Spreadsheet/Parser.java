package Spreadsheet;
import Spreadsheet.Tokenizer.Token;
import Spreadsheet.Exceptions.*;
import java.util.LinkedList;
import java.util.ArrayList;
public class Parser {

        private ArrayList<Integer> savedTokens;
        //private Spreadsheet spreadsheet
        public Parser() {
            savedTokens = new ArrayList<>();
            //this.spreadsheet = spreadsheet;
        }

        public void parse(LinkedList<Token> tokens)  {
            checkBalancedParenthesis(tokens);
            savedTokens.clear();
            if (tokens.get(0).getTokenType() == Tokenizer.MULTDIV || tokens.get(0).getTokenType() == Tokenizer.PLUSMINUS || tokens.get(0).getTokenType() == Tokenizer.CLOSE_BRACKET) {
                throw new ParserException(
                        "Illegal grammar: a formula cannot begin with an operator or a right parenthesis");
            }

            for (Token tok : tokens) {
                int num = tok.getTokenType();

                if (num == Tokenizer.CLOSE_BRACKET) {
                    if (!savedTokens.contains(Tokenizer.OPEN_BRACKET) && !savedTokens.contains(Tokenizer.FUNCTION)  ) {
                        throw new ParserException(
                                "Illegal syntax: unmatched parenthesis");
                    } else if ((!savedTokens.isEmpty()) && ((savedTokens.get(savedTokens.size() - 1) == Tokenizer.MULTDIV) || (savedTokens.get(savedTokens.size() - 1) == Tokenizer.PLUSMINUS))) {
                        throw new ParserException(
                                "Illegal syntax: an operator cannot be followed by a closing parenthesis");}
                    else if ((!savedTokens.isEmpty()) && ((savedTokens.get(savedTokens.size() - 1) == Tokenizer.SEMICOLON) )) {
                        throw new ParserException(
                                "Illegal syntax: a semicolon must be followed by another argument");
                    } else if(savedTokens.contains(Tokenizer.FUNCTION)){
                        ArrayList<Token> functionArguments = new ArrayList<>();

                    }
                }

                else {
                    if (num == Tokenizer.PLUSMINUS || num == Tokenizer.MULTDIV ) {
                        if ((!savedTokens.isEmpty()) && ((savedTokens.get(savedTokens.size() - 1) == Tokenizer.FUNCTION) || (savedTokens.get(savedTokens.size() - 1) == Tokenizer.RANGE || (savedTokens.get(savedTokens.size() - 1) == Tokenizer.SEMICOLON)))) {
                            throw new ParserException(
                                    "Illegal syntax: No operators are allowed inside a function ");}


                    }
                    else if (num == Tokenizer.SEMICOLON) {
                        if ((!savedTokens.isEmpty()) && ((savedTokens.get(savedTokens.size() - 1) == Tokenizer.MULTDIV) || (savedTokens.get(savedTokens.size() - 1) == Tokenizer.PLUSMINUS))) {
                            throw new ParserException(
                                    "Illegal syntax: A semicolon must separate cells, ranges or numbers ");
                        }
                    }
                    if ((!savedTokens.isEmpty()) && (savedTokens.get(savedTokens.size() - 1) == num)) { // ...repeated tokens are not allowed
                        throw new ParserException(
                                "Illegal syntax: repeated tokens");
                    } else if (num == Tokenizer.CELL) {
                        // check that the cells exist; if they don't, raise an exception
                        String coordinates = tok.getTokenString();
                    } else if (num == Tokenizer.RANGE) {
                        String range = tok.getTokenString();
                        Coordinate coord1 = new Coordinate(range.split(":")[0]);
                        Coordinate coord2 = new Coordinate(range.split(":")[1]);

                        if (coord1.getCol()>coord2.getCol()|| coord1.getRow()>coord2.getRow()) {
                            throw new  ParserException("Invalid coordinates: Invalid order of range");
                        }


                    }
                }
                savedTokens.add(num); // save token
            }
        }
        public void checkBalancedParenthesis(LinkedList<Token> tokens){
            int openBrackets = 0;
            for (Token tok : tokens) {
                int num = tok.getTokenType();
                if (num == Tokenizer.OPEN_BRACKET || num == Tokenizer.FUNCTION ){
                    openBrackets++;
                }
                if (num == Tokenizer.CLOSE_BRACKET ){
                    openBrackets--;
                }
                if (openBrackets<0){
                    throw new ParserException("Illegal parenthesis");
                }

            }
            if (openBrackets!=0){throw new ParserException("Illegal parenthesis: unclosed parenthesis");}


    }

}




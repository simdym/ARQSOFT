package Spreadsheet;

import Spreadsheet.Exceptions.*;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {
    public static final int PLUSMINUS = 4;
    public static final int SEMICOLON = 10;
    public static final int MULTDIV = 5;
    public static final int FUNCTION = 1;
    public static final int OPEN_BRACKET = 2;
    public static final int CLOSE_BRACKET = 3;
    public static final int NUMBER = 6;
    public static final int CELL= 8;
    public static final int RANGE = 7;
    private class TokenInfo {
        public final Pattern regex;
        public final int token;

        public TokenInfo(Pattern regex, int token) {
            super();
            this.regex = regex;
            this.token = token;
        }
    }

    private LinkedList<TokenInfo> tokenInfos;
    private LinkedList<Token> tokens;

    public Tokenizer() {
        tokenInfos = new LinkedList<TokenInfo>();
        tokens = new LinkedList<Token>();
        add("(SUM\\(|MAX\\(|MIN\\(|AVG\\()", 1); // function name
        add("\\(", 2); // open bracket
        add("\\)", 3); // close bracket
        add("[\\+\\-]", 4); // operator
        add("[\\*\\/]", 5); // operator
        add("[0-9]+(\\.[0-9]+)?", 6); //number
        add("[A-Z][0-9][:][A-Z][0-9]+", 7);//range
        add("[A-Z][0-9]+", 8); //cell identifier
        add(":", 9);//colon
        add(";", 10);//semi-colon
        add(",", 11);//comma
        add("", 12);//comma


    }
    // cell identifier, colon character, semi-colon character, comma,  and range

    public void add(String regex, int token) {
        tokenInfos.add(
                new TokenInfo(
                        Pattern.compile("^(" + regex + ")"), token));
    }

    public class Token {

        private final int token;// modify to tokentype
        private final String sequence;
        public Token(int token, String sequence) {
            super();
            this.token = token;
            this.sequence = sequence;
        }
        public int getTokenType() {
            return token;
        }
        public String getTokenString() {
            return sequence;
        }
    }



    public void tokenize(String str) {
        String s = new String(str);
        s = s.replaceAll("\\s", "");
        tokens.clear();

        int currentPosition = 0;
        while (currentPosition < s.length()) {
            boolean match = false;
            TokenInfo longestMatchInfo = null;
            String longestMatch = "";

            for (TokenInfo info : tokenInfos) {
                Matcher m = info.regex.matcher(s.substring(currentPosition));
                if (m.find() && m.start() == 0 && m.group().length() > longestMatch.length()) {
                    longestMatch = m.group();
                    longestMatchInfo = info;
                    match = true;
                }
            }

            if (match) {
                tokens.add(new Token(longestMatchInfo.token, longestMatch.trim()));
                currentPosition += longestMatch.length();
            } else {
                throw new ParserException("Unexpected character in input: " + s.charAt(currentPosition));
            }
        }
    }


    public LinkedList<Token> getTokens() {
        return tokens;
    }



    public class ParserException extends RuntimeException {
        public ParserException(String message) {
            super(message);
        }
    }








}

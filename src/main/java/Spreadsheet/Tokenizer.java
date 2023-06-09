package Spreadsheet;

import Spreadsheet.Exceptions.*;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {
    private static class TokenInfo {
        public final Pattern regex;
        public final int token;

        public TokenInfo(Pattern regex, int token) {
            this.regex = regex;
            this.token = token;
        }
    }

    private LinkedList<TokenInfo> tokenInfos;
    private LinkedList<Token> tokens;

    public Tokenizer() {
        tokenInfos = new LinkedList<TokenInfo>();
        tokens = new LinkedList<Token>();
        add( "\\b(SUM\\(|MAX\\(|MIN\\(|AVG\\()", 1); // function name
        add("\\(", 2); // open bracket
        add("\\)", 3); // close bracket
        add("[\\+\\-]", 4); // operator
        add("[\\*\\/]", 5); // operator
        add("[0-9]+", 6); // integer number
        add("[A-Z][0-9][:][A-Z][0-9]+", 7);//range
        add("[A-Z][0-9]+", 8); //cell identifier
        add(":", 9);//colon
        add(";", 10);//semi-colon
        add(",", 11);//comma


    }
    // cell identifier, colon character, semi-colon character, comma,  and range

    public void add(String regex, int token) {
        tokenInfos.add(
                new TokenInfo(
                        Pattern.compile("^(" + regex + ")"), token));
    }

    public class Token {
        public final int token;
        public final String sequence;
        public Token(int token, String sequence) {
            this.token = token;
            this.sequence = sequence;
        }
    }

    public void tokenize(String str) {
        String s = new String(str);
        s = s.replaceAll("\\s", "");
        tokens.clear();
        while (!s.equals("")) {
            boolean match = false;
            for (TokenInfo info : tokenInfos) {
                Matcher m = info.regex.matcher(s);
                if (m.find()) {
                    match = true;
                    String tok = m.group().trim();
                    tokens.add(new Token(info.token, tok));
                    s = m.replaceFirst("");
                    break;
                }
            }
            if (!match) throw new ParserException(
                    "Unexpected character in input: " + s);
        }
    }

    public LinkedList<Token> getTokens() {
        return tokens;
    }

    public static void main(String[] args) {
        Tokenizer tokenizer = new Tokenizer();
        tokenizer.tokenize("sin(x) * (1 + var_12)");

        for (Tokenizer.Token tok : tokenizer.getTokens()) {
            System.out.println("" + tok.token + " " + tok.sequence);
        }
    }

    public class ParserException extends RuntimeException {
        public ParserException(String message) {
            super(message);
        }
    }
}

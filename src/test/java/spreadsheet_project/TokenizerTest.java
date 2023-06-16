package spreadsheet_project;
import java.util.LinkedList;

import edu.upc.etsetb.arqsoft.spreadsheet_project.Formula.*;
import edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet.Coordinate;
import edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet.NumericalContent;
import edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet.Spreadsheet;
import org.junit.jupiter.api.Test;

class TokenizerTest {
    @Test
    void testCalculateFormula() {
        Tokenizer tokenizer = new Tokenizer();
        Spreadsheet spreadsheet = new Spreadsheet();

        spreadsheet.updateContent(new Coordinate("C4"), new NumericalContent("1"));
        spreadsheet.updateContent(new Coordinate("C5"), new NumericalContent("2"));
        spreadsheet.updateContent(new Coordinate("D4"), new NumericalContent("3"));
        spreadsheet.updateContent(new Coordinate("D5"), new NumericalContent("4"));


        tokenizer.tokenize("8-MIN(C4;SUM(MIN(1;5);5);AVG(1;MAX(3;1));D5)-D4");  // result 46
        tokenizer.tokenize("=SUM(1;2)");
        //for (Tokenizer.Token tok : tokenizer.getTokens()) {System.out.println("" + tok.getTokenString() );}
        Parser parser = new Parser();
        parser.setTokens(tokenizer.getTokens());
        parser.parse();
        LinkedList<Tokenizer.Token> parsedTokens = parser.getParsedTokens();
        PostFixGenerator postFixGenerator = new PostFixGenerator();

        LinkedList<Tokenizer.Token> postfixTokens = postFixGenerator.generatePostfix(parsedTokens);
        //

        FormulaComponentFabricator formulaCompFabr = new FormulaComponentFabricator();
        formulaCompFabr.setSpreadsheet(spreadsheet);
        LinkedList<FormulaComponent> formulaTokens =formulaCompFabr.fabricateComponentList(postfixTokens);

        PostFixEvaluator postFixEvaluator = new PostFixEvaluator();
        double result = postFixEvaluator.evaluatePostfix(formulaTokens);
        System.out.println("Result: "+result);
       }
    @Test
    void testEvaluateOperation() {
        Tokenizer.Token token = new Tokenizer().new Token(4, "*");
        Operator operator = new Operator(token);
        double operand1 = 3f;
        double operand2 = 5f;
        double result = operator.evaluateOperation(operand1, operand2);
        System.out.println(result);
    }

    @Test
    void testOperand() {
        Tokenizer tokenizer = new Tokenizer();
        Spreadsheet spreadsheet = new Spreadsheet();
        spreadsheet.updateContent(new Coordinate("C4"), new NumericalContent("8"));
        spreadsheet.updateContent(new Coordinate("C5"), new NumericalContent("1"));
        tokenizer.tokenize("SUM(C4:C5)");
        PostFixGenerator postFixGenerator = new PostFixGenerator();
        LinkedList<Tokenizer.Token> postfixTokens = postFixGenerator.generatePostfix(tokenizer.getTokens());
        //Operand operand = new Operand(postfixTokens);
        //double result = operand.getValue(spreadsheet);
        //System.out.println(result);

    }
    @Test
    void testParse(){
       Tokenizer tokenizer = new Tokenizer();
        Spreadsheet spreadsheet = new Spreadsheet();
        spreadsheet.updateContent(new Coordinate("C4"), new NumericalContent("8"));
        spreadsheet.updateContent(new Coordinate("C5"), new NumericalContent("1"));

        tokenizer.tokenize("(2-7)*8");
        LinkedList<Tokenizer.Token> tokenList = tokenizer.getTokens();



    }

    @Test
    void testFormulaComponentFabricator(){
        Tokenizer tokenizer = new Tokenizer();
        Spreadsheet spreadsheet = new Spreadsheet();

        spreadsheet.updateContent(new Coordinate("C4"), new NumericalContent("1"));
        spreadsheet.updateContent(new Coordinate("C5"), new NumericalContent("2"));
        spreadsheet.updateContent(new Coordinate("D4"), new NumericalContent("3"));
        spreadsheet.updateContent(new Coordinate("D5"), new NumericalContent("4"));


        tokenizer.tokenize("3+SUM(5;6;MAX(C4:D5);MIN(1;3);D5)-(8+9)*4");  // result 46
        //tokenizer.tokenize("C4");
        //for (Tokenizer.Token tok : tokenizer.getTokens()) {System.out.println("" + tok.getTokenString() );}
        Parser parser = new Parser();
        parser.setTokens(tokenizer.getTokens());
        parser.parse();
        PostFixGenerator postFixGenerator = new PostFixGenerator();
        LinkedList<Tokenizer.Token> postfixTokens = postFixGenerator.generatePostfix(tokenizer.getTokens());
        for (Tokenizer.Token tok : postfixTokens) {System.out.println("" + tok.getTokenString() );}

        FormulaComponentFabricator formulaCompFabr = new FormulaComponentFabricator();
        LinkedList<FormulaComponent> formulaTokens = formulaCompFabr.fabricateComponentList(postfixTokens);
        for (FormulaComponent tok : formulaTokens) {System.out.println(tok.getClass());}


    }




// first, get formula coperator and operabnd
//
//

//
//        System.out.println(result);
}








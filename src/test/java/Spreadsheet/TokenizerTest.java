package Spreadsheet;
import java.util.LinkedList;
import Spreadsheet.Coordinate;
import Spreadsheet.Exceptions.InvalidCellIDException;
import Spreadsheet.PostFixGenerator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TokenizerTest {
    @Test
    void testTokenizeFun() {
        Tokenizer tokenizer = new Tokenizer();
        Spreadsheet spreadsheet = new Spreadsheet();
        spreadsheet.updateContent(new Coordinate("C4"), new NumericalContent("1"));
        spreadsheet.updateContent(new Coordinate("C5"), new NumericalContent("2"));
        spreadsheet.updateContent(new Coordinate("D4"), new NumericalContent("3"));
        spreadsheet.updateContent(new Coordinate("D5"), new NumericalContent("4"));


        tokenizer.tokenize("SUM(2;3;SUM(1;50);AVG(1;MAX(3;1)))+9-AVG(1;3)+(3+5)*3-4/2");
        //for (Tokenizer.Token tok : tokenizer.getTokens()) {System.out.println("" + tok.token + " " + tok.sequence);}

        PostFixGenerator postFixGenerator = new PostFixGenerator();
        LinkedList<Tokenizer.Token> postfixTokens = postFixGenerator.generatePostfix(tokenizer.getTokens());
        //

        FormulaComponentFabricator formulaCompFabr = new FormulaComponentFabricator();
        LinkedList<FormulaComponent> formulaTokens = formulaCompFabr.fabricateComponentList(postfixTokens);

        PostFixEvaluator postFixEvaluator = new PostFixEvaluator();
        double result = postFixEvaluator.evaluatePostfix(formulaTokens,spreadsheet);
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
        Operand operand = new Operand(postfixTokens);
        double result = operand.getValue(spreadsheet);
        System.out.println(result);

    }






// first, get formula coperator and operabnd
//
//

//
//        System.out.println(result);
}








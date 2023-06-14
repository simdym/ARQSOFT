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
        spreadsheet.updateContent(new Coordinate("C4"), new NumericalContent("8"));
        spreadsheet.updateContent(new Coordinate("C5"), new NumericalContent("1"));


        tokenizer.tokenize("2+(8/4)+3+SUMA(3;2)");
        //for (Tokenizer.Token tok : tokenizer.getTokens()) {System.out.println("" + tok.token + " " + tok.sequence);}

        PostFixGenerator postFixGenerator = new PostFixGenerator();
        LinkedList<Tokenizer.Token> postfixTokens = postFixGenerator.generatePostfix(tokenizer.getTokens());
        //for (Tokenizer.Token tok : postfixTokens) {System.out.println("" + tok.token + " " + tok.sequence);}

        FormulaComponentFabricator formulaCompFabr = new FormulaComponentFabricator();
        LinkedList<FormulaComponent> formulaTokens = formulaCompFabr.fabricateComponentList(postfixTokens);

        PostFixEvaluator postFixEvaluator = new PostFixEvaluator();
        double result = postFixEvaluator.evaluatePostfix(formulaTokens,spreadsheet);
        System.out.println(result);
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








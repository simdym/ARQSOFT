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
        tokenizer.tokenize("3+MAX(3.88;AVG(2;3))-MIN(2;1)+(26*3)");
        //for (Tokenizer.Token tok : tokenizer.getTokens()) {System.out.println("" + tok.token + " " + tok.sequence);}

        PostFixGenerator postFixGenerator = new PostFixGenerator();
        LinkedList<Tokenizer.Token> postfixTokens = postFixGenerator.generatePostfix(tokenizer.getTokens());
        //for (Tokenizer.Token tok : postfixTokens) {System.out.println("" + tok.token + " " + tok.sequence);}

        FormulaComponentFabricator formulaCompFabr = new FormulaComponentFabricator();
        LinkedList<FormulaComponent> formulaTokens = formulaCompFabr.fabricateComponentList(postfixTokens);

        //PostFixEvaluator postFixEvaluator = new PostFixEvaluator(spreadsheet);
        //double result = postFixEvaluator.evaluatePostfix(formulaTokens);

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
        spreadsheet.updateContent(new Coordinate("C4"), new NumericalContent("89"));
        tokenizer.tokenize("MIN(100;10;20)");
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








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
        tokenizer.tokenize("6*2/(4+20)*30");
        for (Tokenizer.Token tok : tokenizer.getTokens()) {
            //System.out.println("" + tok.token + " " + tok.sequence);
        }
        PostFixGenerator postFixGenerator = new PostFixGenerator();
        LinkedList<Tokenizer.Token> postfixTokens = postFixGenerator.generatePostfix(tokenizer.getTokens());
        // Output the postfix expression
        for (Tokenizer.Token tok : postfixTokens) {
            //System.out.println("" + tok.token + " " + tok.sequence);
        }
        PostFixEvaluator postFixEvaluator = new PostFixEvaluator();
        float result = postFixEvaluator.evaluatePostfix(postfixTokens);
        System.out.println(result);
    }

}
package Spreadsheet;

import Spreadsheet.Coordinate;
import Spreadsheet.Exceptions.InvalidCellIDException;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TokenizerTest {
    @Test
    void testTokenizeFun() {
        Tokenizer tokenizer = new Tokenizer();
        tokenizer.tokenize("SUM(A1:B4)+6*4");
        for (Tokenizer.Token tok : tokenizer.getTokens()) {
            System.out.println("" + tok.token + " " + tok.sequence);
        }

    }

}
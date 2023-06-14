package Spreadsheet;

import java.util.Collections;
import java.util.LinkedList;

public class Operand extends FormulaComponent {
    public Operand(LinkedList<Tokenizer.Token> operandToken){
        super(operandToken);

    }
    //Has method getValue
    //Can be numerical, cell, range or another function
}

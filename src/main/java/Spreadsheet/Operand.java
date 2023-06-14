package Spreadsheet;

import java.util.Collections;
import java.util.LinkedList;

public class Operand extends FormulaComponent {
    private Spreadsheet spreadsheet;
    private LinkedList<Tokenizer.Token> operandTokens;
    public Operand(LinkedList<Tokenizer.Token> operandTokens){
        super(operandTokens);

    }
    public double getValue(){
        Tokenizer.Token token = operandTokens.getFirst();
        double value=0;
        if (token.token==6){//if number
            value = new NumericalValue(token.sequence).getValue();

        }
        if (token.token==8) {//if cells
            Coordinate coord = new Coordinate(token.sequence);
            Cell cell = spreadsheet.getCell(coord);
            Content  content=  cell.getContent();
            Object valueObj=  content.getValue().getValue();
            value = (double) valueObj;

        }
        else if (token.token == 1){ //Then it's function
            Function function = Function.FunctionFactory.createFunction(token.sequence);
            int nestedFun = 0;
            for (int i = 1; i < operandTokens.size(); i++) {

                if (token.token==6){//if number
                    Argument arg = new NumericalValue(token.sequence);
                    function.addArgument(arg);
                }
                if (token.token==7){//if range
                    String[] coordString = token.sequence.split(":");
                    Coordinate coord1 = new Coordinate(coordString [0]);
                    Coordinate coord2 = new Coordinate(coordString [1]);
                    Argument arg = new CellRange(coord1,coord2,spreadsheet);
                    function.addArgument(arg);
                }
                if (token.token==8){// if cell
                    Coordinate coord = new Coordinate(token.sequence);
                    Argument arg = spreadsheet.getCell(coord);
                    function.addArgument(arg);
                }
                if (token.token==1){// if another function
                    //add all

                }

            }
            value = function.computeValue();
        }

        return value;
    }

}

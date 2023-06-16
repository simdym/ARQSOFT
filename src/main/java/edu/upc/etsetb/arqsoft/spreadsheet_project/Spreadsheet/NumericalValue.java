package edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet;

import edu.upc.etsetb.arqsoft.spreadsheet_project.Formula.Argument;
import edu.upc.etsetb.arqsoft.spreadsheet_project.Formula.Operand;

/**
 * NumericalValue is a subclass of Value. It represents the value of a cell that is a number (NUMERICALCONTENT).
 * It contains the value of the cell as a double.
 */
public class NumericalValue implements Value , Argument, Operand {
    private final double value;

    public NumericalValue(double value) {
        this.value = value;
    }

    public NumericalValue(String strValue) {
        if (strValue == null || strValue.equals("")){
            this.value = 0.0;
        } else {
            this.value = Double.parseDouble(strValue);
        }
    }

    @Override
    public Double getValue() {return value;}

    @Override
    public double getValueAsDouble() { return value; }//Used for formula processing

    @Override
    public String getValueAsString() { return Double.toString(value); }

    @Override
    public double getDoubleValue() {return value;}
}

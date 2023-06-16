package edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet;

import edu.upc.etsetb.arqsoft.spreadsheet_project.Formula.Argument;
import edu.upc.etsetb.arqsoft.spreadsheet_project.Formula.Operand;

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
    public double getValueAsDouble() { return value; }

    @Override
    public String getValueAsString() { return Double.toString(value); }

    @Override
    public double getDoubleValue() {return value;}
}

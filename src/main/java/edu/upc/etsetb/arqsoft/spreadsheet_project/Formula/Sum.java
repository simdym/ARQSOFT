package edu.upc.etsetb.arqsoft.spreadsheet_project.Formula;

public class Sum extends Function {
    public double getDoubleValue(){
        float sum = 0;
        for (Argument argument : arguments) {
            sum += argument.getDoubleValue();
        }
        return sum;
    }
}

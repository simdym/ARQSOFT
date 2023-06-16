package edu.upc.etsetb.arqsoft.spreadsheet_project.Formula;

public class Max extends Function {
    public double getDoubleValue(){

        double max = arguments.getFirst().getDoubleValue();
        for (Argument argument : arguments) {
            double value = argument.getDoubleValue();
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
    //return Collections.max(arguments);
}

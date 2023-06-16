package edu.upc.etsetb.arqsoft.spreadsheet_project.Formula;

/**
 *
 * Calculates the max of the arguments. Subclass of Function.
 */
public class Max extends Function {
    public double getDoubleValue(){

        double max = arguments.getFirst().getDoubleValue();
        for (Argument argument : arguments) {
            double value = argument.getDoubleValue();
            if (value > max) {
                max = value;//if value is greater than max, max is equal to value
            }
        }
        return max;
    }
    //return Collections.max(arguments);
}

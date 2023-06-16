package edu.upc.etsetb.arqsoft.spreadsheet_project.Formula;

/**
 *
 * Calculates the max of the arguments. Subclass of Function.
 */
public class Min extends Function {
    public double getDoubleValue() {
        double min = arguments.getFirst().getDoubleValue();
        for (Argument argument : arguments) {
            double value = argument.getDoubleValue();
            if (value < min) {
                min = value;// if value is less than min, min is updatesto value
            }
        }
        return min;
    }
}

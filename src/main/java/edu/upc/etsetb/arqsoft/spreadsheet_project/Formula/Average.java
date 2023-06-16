package edu.upc.etsetb.arqsoft.spreadsheet_project.Formula;

/**
 *
 * Calculates the average of the arguments. Subclass of Function.
 */
public class Average extends Function {
    public double getDoubleValue(){
        double sum = 0.0f;
        for (Argument argument : arguments) {
            sum += argument.getDoubleValue(); //sum is equal to sum plus the value of the argument
        }
        double result = sum / arguments.size(); //divide by the number of arguments
        return result;

    };
}

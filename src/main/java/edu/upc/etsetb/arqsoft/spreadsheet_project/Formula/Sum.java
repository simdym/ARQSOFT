package edu.upc.etsetb.arqsoft.spreadsheet_project.Formula;

public class Sum extends Function {
    public double getDoubleValue(){
        float sum = 0;
        for (Argument argument : arguments) {
            sum += argument.getDoubleValue();
        }
        //System.out.println("sum"+sum);
        return sum;
    };
    /*float sum = 0;

    return sum;*/
}

package Spreadsheet;

public class Sum extends Function{
    public double getDoubleValue(){
        float sum = 0;
        for (Argument argument : arguments) {
            sum += argument.getDoubleValue();
        }
        return sum;
    };
    /*float sum = 0;

    return sum;*/
}

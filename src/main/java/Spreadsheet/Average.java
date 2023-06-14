package Spreadsheet;

public class Average extends Function{
    public double getDoubleValue(){
        float sum = 0.0f;
        for (Argument argument : arguments) {
            sum += argument.getDoubleValue();
        }
        return sum / arguments.size();
    };
}

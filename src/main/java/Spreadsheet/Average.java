package Spreadsheet;

public class Average extends Function{
    public double getDoubleValue(){
        double sum = 0.0f;
        for (Argument argument : arguments) {
            sum += argument.getDoubleValue();
        }
        double result = sum / arguments.size();
        //System.out.println("avg"+result);
        return result;

    };
}

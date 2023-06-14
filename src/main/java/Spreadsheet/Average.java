package Spreadsheet;

public class Average extends Function{
    public double computeValue(){
        float sum = 0.0f;
        for (Argument argument : arguments) {
            //sum += argument.getValue();
        }
        return sum;// / arguments.size();
    };
    /*float total = 0;
                    for (float argument : arguments) {
        total += argument;
    }
                    return total / arguments.size();*/
}

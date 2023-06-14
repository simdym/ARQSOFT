package Spreadsheet;

public class Sum extends Function{
    public float computeValue(){
        float sum = 0;
        for (Argument argument : arguments) {
            //sum += argument.getValue();
        }
        return sum;
    };
    /*float sum = 0;

    return sum;*/
}

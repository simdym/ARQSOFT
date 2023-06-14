package Spreadsheet;

public class Min extends Function {
    public double getDoubleValue() {
        double min = arguments.getFirst().getDoubleValue();
        for (Argument argument : arguments) {
            double value = argument.getDoubleValue();
            if (value < min) {
                min = value;
            }
        }
        return min;
    }
}

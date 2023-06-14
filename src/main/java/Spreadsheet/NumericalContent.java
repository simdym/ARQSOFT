package Spreadsheet;

public class NumericalContent extends Content {
    public NumericalContent(String value) {
        super(value, new NumericalValue(value));
    }
}

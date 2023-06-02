package Spreadsheet;

public class NumericalContent extends Content {
    public NumericalContent(String strValue) {
        super(strValue, new NumericalValue(strValue));
    }
}

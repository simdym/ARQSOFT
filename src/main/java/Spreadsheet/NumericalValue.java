package Spreadsheet;

public class NumericalValue implements Value {
    private double value;

    NumericalValue(double value) {
        this.value = value;
    }

    NumericalValue(String strValue) {
        this.value = Double.parseDouble(strValue);
    }

    public Double getValue() {return value;}
}

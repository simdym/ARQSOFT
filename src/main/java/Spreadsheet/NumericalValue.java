package Spreadsheet;

public class NumericalValue implements Value ,Argument{
    private final double value;

    NumericalValue(double value) {
        this.value = value;
    }

    NumericalValue(String strValue) {
        if (strValue == null || strValue.equals("")){
            this.value = 0.0;
        } else {
            this.value = Double.parseDouble(strValue);
        }
    }

    public Double getValue() {return value;}
}

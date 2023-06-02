package Spreadsheet;

public class TextValue implements Value {
    String value;
    TextValue(String value) {
        this.value = value;
    }

    public String getValue() {return value;}
}

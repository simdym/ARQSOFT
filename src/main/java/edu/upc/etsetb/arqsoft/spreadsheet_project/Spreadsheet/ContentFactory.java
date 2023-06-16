package edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet;

public class ContentFactory {

    public ContentFactory() {}

    public static Content createContent(String contentStr) {
        Content resContent = null;
        if(isFormula(contentStr)) {
            resContent = new FormulaContent(contentStr);
        } else {
            if(isNumberString(contentStr)) {
                resContent = new NumericalContent(contentStr);
            } else {
                resContent = new TextContent(contentStr);
            }
        }
        return resContent;
    }

    private static boolean isFormula(String value) {
        return value.charAt(0) == '=';
    }

    private static boolean isNumberString(String value) {
        try {
            Double.parseDouble(value);
        }
        catch(NumberFormatException e) {
            return false;
        }
        return true;
    }
}

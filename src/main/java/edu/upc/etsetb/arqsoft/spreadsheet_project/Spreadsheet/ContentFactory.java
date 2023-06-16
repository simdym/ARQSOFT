package edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet;

/**
 *
 * Fabricates the content of a cell using a string. it checks if the string is a formula, a number or a text and creates the corresponding content.
 *
 */
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

    private static boolean isFormula(String value) { //If starting char is an equal sign, it is a formula
        return value.charAt(0) == '=';
    }

    private static boolean isNumberString(String value) { //If the text is parseable to a double, it is a number
        try {
            Double.parseDouble(value);
        }
        catch(NumberFormatException e) {
            return false;
        }
        return true;
    }
}

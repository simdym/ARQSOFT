package Spreadsheet;

public class ContentFactory {

    public ContentFactory() {}

    public Content createContent(String contentStr) {
        Content resContent = null;
        if(isFormula(contentStr)) {
            // Formula

            //TODO: finish FormulaContent
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

    private boolean isFormula(String value) {
        return value.charAt(0) == '=';
    }

    private boolean isNumberString(String value) {
        try {
            Double.parseDouble(value);
        }
        catch(NumberFormatException e) {
            return false;
        }
        return true;
    }
}

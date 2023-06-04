package Spreadsheet;

import Spreadsheet.Cmd.Cmd;

public class Controller {
    public void run() {}
    private void processUserCommand(Cmd cmd){}
    private void createNewSpreadsheet(){}
    private void loadExistingSpreadsheet(String filepath){}
    private void saveSpreadsheet(String filename){}
    private void modifyCellContent(Spreadsheet ss, String cellId, String newValue){
        Coordinate cellCoor = new Coordinate(cellId);
        Content newContent = stringToContent(newValue);
        ss.updateContent(cellCoor, newContent);
    }

    private Content stringToContent(String contentStr) {
        Content resContent;
        if(isFormula(contentStr)) {
            // Formula

            //TODO: finish FormulaContent
            resContent = new FormulaContent();
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
    //private double computeFormula(Cell cell){}
    private void deleteContent(String cellId){}
    //public Content retrieveCellContent(Coordinate coordinate){}




}

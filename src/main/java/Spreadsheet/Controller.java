package Spreadsheet;

public class Controller {
    public void run() {}
    private void processUserCommand(Cmd cmd){}
    private void createNewSpreadsheet(){}
    private void loadExistingSpreadsheet(String filepath){}
    private void saveSpreadsheet(String filename){}
    private void modifyCellContent(Spreadsheet ss, String cellId, String newValue){
        Coordinate cellCoor = new Coordinate(cellId);

        if(isFormula(newValue)) {
            // Formula

            //TODO: finish FormulaContent
            //FormulaContent newContent = FormulaContent();
        } else {
            if(isNumberString(newValue)) {
                NumericalContent newContent = new NumericalContent(newValue);
                ss.updateContent(cellCoor, newContent);
            } else {
                TextContent newContent = new TextContent(newValue);
                ss.updateContent(cellCoor, newContent);
            }
        }
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

package Spreadsheet.Cmd;

public class ECmd implements Cmd {
    private final String cellID;
    private final String contentStr;

    public ECmd(String cellID, String contentStr) {
        this.cellID = cellID;
        this.contentStr = contentStr;
    }

    @Override
    public String getType() {
        return "E";
    }

    public String getCellID() {
        return cellID;
    }

    public String getContentStr() {
        return contentStr;
    }
}

package Spreadsheet.Cmd;

public class SCmd implements Cmd {
    private final String filepath;

    public SCmd(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public String getType() {
        return "S";
    }

    public String getFilepath() {
        return filepath;
    }
}

package Spreadsheet.Cmd;

public class LCmd implements Cmd {
    private final String filepath;

    public LCmd(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public String getType() {
        return "L";
    }

    public String getFilepath() {
        return filepath;
    }
}

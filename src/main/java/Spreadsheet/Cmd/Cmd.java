package Spreadsheet.Cmd;

public class Cmd {
    CmdType cmdType;
    String[] arguments;
    public Cmd(CmdType cmdType, String[] arguments) {
        this.cmdType = cmdType;
        this.arguments = arguments;
    }

    public CmdType getCmdType() {
        return cmdType;
    }

    public String getArgument(int index) {
        return arguments[index];
    }
}

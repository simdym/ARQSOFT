package edu.upc.etsetb.arqsoft.spreadsheet_project.Cmd;

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

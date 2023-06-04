package Spreadsheet.Cmd;

import java.util.ArrayList;

public class RFCmd implements Cmd {
    private final ArrayList<Cmd> commands;

    public RFCmd(ArrayList<Cmd> commands) {
        this.commands = commands;
    }

    @Override
    public String getType() {
        return "RF";
    }

    public ArrayList<Cmd> getCommands() {
        return commands;
    }
}

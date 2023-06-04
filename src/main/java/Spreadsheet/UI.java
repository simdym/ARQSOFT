package Spreadsheet;

import Spreadsheet.Cmd.*;

import java.util.Scanner;

public class UI {
    public UI(){}

    public Cmd askForCmd() {
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter command");

        String commandStr = myObj.nextLine();

        return readCmd(commandStr);
    }

    private Cmd readCmd(String commandStr) {
        String[] cmdParts = commandStr.split("\\s+");

        Cmd command;

        switch (cmdParts[0]) {
            case "RF":
                //TODO
                //open file
                //make commands out of file contents
                //call new RFCmd(commands)
            case "C":
                command = new CCmd();
                break;
            case "E":
                command = new ECmd(cmdParts[1], cmdParts[2]);
                break;
            case "L":
                command = new LCmd(cmdParts[1]);
                break;
            case "S":
                command = new SCmd(cmdParts[1]);
                break;
            default:
                command = null;
                break;
                //TODO invalidCmdException
        }

        return command;
    }
}

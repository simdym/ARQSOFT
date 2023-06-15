package Spreadsheet;

import Spreadsheet.Cmd.*;
import Spreadsheet.Exceptions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class UI {
    public UI(){}

    public Cmd askForCmd() throws FileNotFoundException {
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter command");

        String commandStr = myObj.nextLine(); // Read command

        return readCmd(commandStr);
    }

    private Cmd readCmd(String commandStr) throws FileNotFoundException, CommandFormatException {
        String[] cmdParts = commandStr.split("\\s+"); // Split command into parts
        String[] arguments = Arrays.copyOfRange(cmdParts, 1, cmdParts.length);
        String cmdTypeStr = cmdParts[0];

        Cmd command;

        if (cmdParts.length > 3) {
            throw new CommandFormatException("The command does not follow the expected format");
        }

        switch (cmdTypeStr) {
            case "RF":
                if (cmdParts.length != 2) {
                    throw new CommandFormatException("The command does not follow the expected format");
                }
                /* System.out.println(cmdParts[1]);
                File file = new File(cmdParts[1]);
                Scanner sc = new Scanner(file);

                ArrayList<Cmd> commands = new ArrayList<Cmd>();
                while (sc.hasNextLine()) {
                    String str = sc.nextLine();
                    if(!str.startsWith("RF")) { // Check that the command is not another RF-command
                        Cmd newCmd = readCmd(str);
                        commands.add(newCmd);
                    }
                } */
                command = new Cmd(CmdType.RUN_FROM_FILE, arguments);
                break;
            case "C":
                if (cmdParts.length != 1) {
                    throw new CommandFormatException("The command does not follow the expected format");
                }
                command = new Cmd(CmdType.CREATE_SPREADSHEET, arguments);
                break;
            case "E":
                if (cmdParts.length != 3) {
                    throw new CommandFormatException("The command does not follow the expected format");
                }
                command = new Cmd(CmdType.EDIT_CELL, arguments);
                break;
            case "L":
                if (cmdParts.length != 2) {
                    throw new CommandFormatException("The command does not follow the expected format");
                }
                command = new Cmd(CmdType.LOAD_SPREADSHEET, arguments);
                break;
            case "S":
                if (cmdParts.length != 2) {
                    throw new CommandFormatException("The command does not follow the expected format");
                }
                command = new Cmd(CmdType.SAVE_SPREADSHEET, arguments);
                break;
            default:
                throw new CommandFormatException("No such command exist: " + cmdTypeStr);
        }

        return command;
    }
}

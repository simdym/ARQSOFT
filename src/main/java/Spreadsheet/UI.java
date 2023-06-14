package Spreadsheet;

import Spreadsheet.Cmd.*;
import Spreadsheet.Exceptions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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

        Cmd command;

        if (cmdParts.length > 3) {
            throw new CommandFormatException("The command does not follow the expected format");
        }

        switch (cmdParts[0]) {
            case "RF":
                if (cmdParts.length != 2) {
                    throw new CommandFormatException("The command does not follow the expected format");
                }
                System.out.println(cmdParts[1]);
                File file = new File(cmdParts[1]);
                Scanner sc = new Scanner(file);

                ArrayList<Cmd> commands = new ArrayList<Cmd>();
                while (sc.hasNextLine()) {
                    String str = sc.nextLine();
                    if(!str.startsWith("RF")) { // Check that the command is not a RF-command
                        Cmd newCmd = readCmd(str);
                        commands.add(newCmd);
                    }
                }
                command = new RFCmd(commands);
                break;
            case "C":
                if (cmdParts.length != 1) {
                    throw new CommandFormatException("The command does not follow the expected format");
                }
                command = new CCmd();
                break;
            case "E":
                if (cmdParts.length != 3) {
                    throw new CommandFormatException("The command does not follow the expected format");
                }
                command = new ECmd(cmdParts[1], cmdParts[2]);
                break;
            case "L":
                if (cmdParts.length != 2) {
                    throw new CommandFormatException("The command does not follow the expected format");
                }
                command = new LCmd(cmdParts[1]);
                break;
            case "S":
                if (cmdParts.length != 2) {
                    throw new CommandFormatException("The command does not follow the expected format");
                }
                command = new SCmd(cmdParts[1]);
                break;
            default:
                throw new CommandFormatException("No such command exist: " + cmdParts[0]);
        }

        return command;
    }
}

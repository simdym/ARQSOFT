package edu.upc.etsetb.arqsoft.spreadsheet_project.Cmd;

import edu.upc.etsetb.arqsoft.spreadsheet_project.Exceptions.CommandFormatException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CmdFactory {
    public static ArrayList<Cmd> readCmdFromFile(String filepath) throws FileNotFoundException {
        File file = new File(filepath);
        Scanner sc = new Scanner(file);

        ArrayList<Cmd> commands = new ArrayList<Cmd>();

        // Read each line in file
        while (sc.hasNextLine()) {
            String str = sc.nextLine();
            if(!str.startsWith("RF")) { // Check that the command is not another RF-command
                Cmd newCmd = readCmd(str);
                commands.add(newCmd);
            }
        }


        return commands;
    }

    public static Cmd readCmd(String commandStr) throws CommandFormatException {

        // Split command into parts
        String[] cmdParts = commandStr.split("\\s+");

        // Get arguments
        String[] arguments = Arrays.copyOfRange(cmdParts, 1, cmdParts.length);
        String cmdTypeStr = cmdParts[0];

        Cmd command;

        switch (cmdTypeStr) {
            case "RF":
                // Make RUN_FROM_FILE-command

                if (cmdParts.length != 2) {
                    throw new CommandFormatException("The command does not follow the expected format");
                }
                command = new Cmd(CmdType.RUN_FROM_FILE, arguments);
                break;
            case "C":
                // Make CREATE_SPREADSHEET-command

                if (cmdParts.length != 1) {
                    throw new CommandFormatException("The command does not follow the expected format");
                }
                command = new Cmd(CmdType.CREATE_SPREADSHEET, arguments);
                break;
            case "E":
                // Make EDIT_CELL-command

                if (cmdParts.length < 3) {
                    throw new CommandFormatException("The command does not follow the expected format");
                }
                // In case of a string with spaces
                String[] contentArray = Arrays.copyOfRange(arguments, 1, arguments.length);
                String textContent = String.join(" ", contentArray);

                command = new Cmd(CmdType.EDIT_CELL, new String[]{arguments[0], textContent});
                break;
            case "L":
                // Make LOAD_SPREADSHEET-command

                if (cmdParts.length != 2) {
                    throw new CommandFormatException("The command does not follow the expected format");
                }
                command = new Cmd(CmdType.LOAD_SPREADSHEET, arguments);
                break;
            case "S":
                // Make SAVE_SPREADSHEET-command

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

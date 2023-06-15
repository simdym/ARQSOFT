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

        return CmdFactory.readCmd(commandStr);
    }
}

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

    public void displaySpreadsheet(Spreadsheet spreadsheet) {
        int maxRow = spreadsheet.getMaxRow();
        int maxCol = spreadsheet.getMaxColumn();

        for (int row = 0; row < (maxRow + 1); row++) {
            StringBuilder columnString = new StringBuilder();
            for (int col = 0; col < (maxCol + 1); col++) {
                // Retrieve content and convert to String
                Content content = spreadsheet.getContent(new Coordinate(row, col));
                String stringContent = "";
                if (content != null) {
                    stringContent = String.valueOf(content.getValue().getValue());
                }
                // Append to columnString
                columnString.append(stringContent).append(";");
            }
            //appends the string to the file
            System.out.println(columnString.toString());
        }
    }
}

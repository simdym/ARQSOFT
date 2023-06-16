package edu.upc.etsetb.arqsoft.spreadsheet_project.Framework;

import edu.upc.etsetb.arqsoft.spreadsheet_project.Cmd.Cmd;
import edu.upc.etsetb.arqsoft.spreadsheet_project.Cmd.CmdFactory;
import edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet.Content;
import edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet.Coordinate;
import edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet.Spreadsheet;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class UI {
    public UI(){}

    public Cmd askForCmd() throws FileNotFoundException {
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter command");

        String commandStr = myObj.nextLine(); // Read command

        return CmdFactory.readCmd(commandStr);
    }

    private String fixedLengthString(String string, int length) {
        return String.format("%1$"+length+ "s", string);
    }

    public void displaySpreadsheet(Spreadsheet spreadsheet) {
        ArrayList<Integer> relevantRows = spreadsheet.getRelevantRows();
        ArrayList<Integer> relevantCols = spreadsheet.getRelevantCols();

        String diagonalHeader = "col\\rows";
        int maxWidth = spreadsheet.getMaxWidth();
        if(maxWidth < diagonalHeader.length()) {
            maxWidth = diagonalHeader.length();
        }



        // Print headers
        StringBuilder headers = new StringBuilder();
        headers.append(" | ");
        headers.append(fixedLengthString(diagonalHeader, maxWidth));
        headers.append(" | ");
        for (Integer col: relevantCols) {
            //Calculate character representation of column
            StringBuilder column = new StringBuilder();

            while (col >= 0) {
                int remainder = col % 26;
                char character = (char) ('A' + remainder);
                column.insert(0, character);
                col = (col / 26) - 1;
            }
            String columnStr = fixedLengthString(column.toString(), 10);
            headers.append(columnStr);
            headers.append(" | ");
        }
        System.out.println(headers);

        for (Integer row: relevantRows) {
            StringBuilder columnString = new StringBuilder();
            columnString.append(" | ");
            String rowIndex = fixedLengthString(String.valueOf(row + 1), maxWidth);
            columnString.append(rowIndex);
            columnString.append(" | ");
            for (Integer col: relevantCols) {

                // Retrieve content and convert to String
                Content content = spreadsheet.getContent(new Coordinate(row, col));
                String stringContent = "";
                if (content != null) {
                    stringContent = String.valueOf(content.getValue().getValue());
                }
                // Append to columnString
                stringContent = fixedLengthString(stringContent, 10);
                columnString.append(stringContent);
                columnString.append(" | ");
            }
            //appends the string to the file
            System.out.println(columnString);
        }
    }
}

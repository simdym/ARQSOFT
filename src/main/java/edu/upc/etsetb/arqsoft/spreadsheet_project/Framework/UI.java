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
        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter command");

        // Read command
        String commandStr = myObj.nextLine();

        // Create command from string
        return CmdFactory.readCmd(commandStr);
    }

    private String fixedLengthString(String string, int length) {
        // Get string of given length
        return String.format("%1$"+length+ "s", string);
    }

    public void displaySpreadsheet(Spreadsheet spreadsheet) {
        // Get all columns and rows with cells
        ArrayList<Integer> relevantRows = spreadsheet.getRelevantRows();
        ArrayList<Integer> relevantCols = spreadsheet.getRelevantCols();

        // String up to the left
        String diagonalHeader = "rows\\cols";

        // Get max length of values
        int maxWidth = spreadsheet.getMaxWidth();
        if(maxWidth < diagonalHeader.length()) {
            maxWidth = diagonalHeader.length();
        }

        // Add headers
        StringBuilder headers = new StringBuilder();
        headers.append(" | ");
        headers.append(fixedLengthString(diagonalHeader, maxWidth));
        headers.append(" | ");


        for (Integer col: relevantCols) {
            // Calculate character representation of column index
            StringBuilder columnIndex = new StringBuilder();
            while (col >= 0) {
                int remainder = col % 26;
                char character = (char) ('A' + remainder);
                columnIndex.insert(0, character);
                col = (col / 26) - 1;
            }

            // Add to header with given width
            String columnStr = fixedLengthString(columnIndex.toString(), 10);
            headers.append(columnStr);
            headers.append(" | ");
        }

        // Print headers
        System.out.println(headers);

        // Print all columns and rows
        for (Integer row: relevantRows) {
            
            // Add rows
            StringBuilder rowString = new StringBuilder();
            rowString.append(" | ");
            String rowIndex = fixedLengthString(String.valueOf(row + 1), maxWidth);
            rowString.append(rowIndex);
            rowString.append(" | ");
            for (Integer col: relevantCols) {

                // Retrieve content and convert to String
                Content content = spreadsheet.getContent(new Coordinate(row, col));
                String stringContent = "";
                if (content != null) {
                    stringContent = String.valueOf(content.getValue().getValue());
                }

                // Append to columnString
                stringContent = fixedLengthString(stringContent, 10);
                rowString.append(stringContent);
                rowString.append(" | ");
            }
            
            // Print row
            System.out.println(rowString);
        }
    }
}

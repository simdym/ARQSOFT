package edu.upc.etsetb.arqsoft.spreadsheet_project.Framework;

import edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet.*;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class FileManager {
    public FileManager() {}

    public ArrayList<String[]> loadSpreadsheet(String filepath) throws FileNotFoundException {
        File file = new File(filepath);
        Scanner sc = new Scanner(file);
        ArrayList<String[]> rows = new ArrayList<String[]>();

        while (sc.hasNextLine()) {
            // Reads line from file
            String str = sc.nextLine();

            // Split
            String[] rowContents = str.split(";", -1);
            for (int i = 0; i < rowContents.length; ++i) {
                rowContents[i] = rowContents[i].replace(',', ';');
            }

            rows.add(rowContents);
        }
        return rows;
    }

    public void saveSpreadsheet(Spreadsheet originSpreadsheet, String filepath) throws IOException {
        // Finds the max row and column of the spreadsheet
        int maxRow = originSpreadsheet.getMaxRow();
        int maxCol = originSpreadsheet.getMaxColumn();

        // Make file writer
        FileWriter fw = new FileWriter(filepath);
        BufferedWriter bw = new BufferedWriter(fw);

        try {
            // Loop over all cells
            for (int row = 0; row < (maxRow + 1); row++) {
                StringBuilder columnString = new StringBuilder();
                for (int col = 0; col < (maxCol + 1); col++) {
                    // Retrieve content and convert to String
                    Content content = originSpreadsheet.getContent(new Coordinate(row, col));
                    String stringContent = "";
                    if (content != null) {
                        stringContent = String.valueOf(content.getSaveableString());
                    }
                    // Append to columnString
                    columnString.append(stringContent).append(";");
                }
                //appends the string to the file
                bw.write(columnString.toString().replaceAll(";+$", ""));
                bw.newLine();
            }
            bw.close();
            fw.close();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void SpreadsheetToS2V(String filename){}
}


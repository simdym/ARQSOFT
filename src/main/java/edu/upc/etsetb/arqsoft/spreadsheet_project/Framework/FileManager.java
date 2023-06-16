package edu.upc.etsetb.arqsoft.spreadsheet_project.Framework;

import edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet.Content;
import edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet.ContentFactory;
import edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet.Coordinate;
import edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet.Spreadsheet;

import java.io.*;
import java.util.Scanner;

public class FileManager {
    public FileManager() {}

    public void loadSpreadsheet(String filepath, Spreadsheet targetSpreadsheet) throws FileNotFoundException {
        File file = new File(filepath);
        Scanner sc = new Scanner(file);

        int row = 0;
        while (sc.hasNextLine()) {
            String str = sc.nextLine();

            String[] cellContents = str.split(";", -1);

            for(int col = 0; col < cellContents.length; col++) {
                if(cellContents[col] != "") {
                    Content newContent = ContentFactory.createContent(cellContents[col]);
                    targetSpreadsheet.updateContent(new Coordinate(row, col), newContent);
                }
            }

            row++;
        }
    }
    //private Spreadsheet S2VToSpreadsheet(String filename){}
    public void saveSpreadsheet(Spreadsheet originSpreadsheet, String filepath) throws IOException {
        int maxRow = originSpreadsheet.getMaxRow();
        int maxCol = originSpreadsheet.getMaxColumn();

        FileWriter fw = new FileWriter(filepath);
        BufferedWriter bw = new BufferedWriter(fw);

        try {
            for (int row = 0; row < (maxRow + 1); row++) {
                StringBuilder columnString = new StringBuilder();
                for (int col = 0; col < (maxCol + 1); col++) {
                    // Retrieve content and convert to String
                    Content content = originSpreadsheet.getContent(new Coordinate(row, col));
                    String stringContent = "";
                    if (content != null) {
                        stringContent = String.valueOf(content.getContent());
                    }
                    // Append to columnString
                    columnString.append(stringContent).append(";");
                }
                //appends the string to the file
                bw.write(columnString.toString());
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


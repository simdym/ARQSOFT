package Spreadsheet;

import Spreadsheet.Cmd.Cmd;

import java.io.*;
import java.util.Scanner;

public class FileManager {
    public FileManager() {}

    public Spreadsheet loadSpreadsheet(String filepath, Spreadsheet targetSpreadsheet) throws FileNotFoundException {
        File file = new File(filepath);
        Scanner sc = new Scanner(file);
        ContentFactory cf = new ContentFactory();

        int row = 0;
        while (sc.hasNextLine()) {
            String str = sc.nextLine();

            String[] cellContents = str.split(";");

            for(int col = 0; col < cellContents.length; col++) {
                Content newContent = cf.createContent(cellContents[col]);
                targetSpreadsheet.updateContent(new Coordinate(row, col), newContent);
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
            for (int col = 0; col < (maxCol + 1); col++) {
                StringBuilder columnString = new StringBuilder();
                for (int row = 0; row < (maxRow + 1); row++) {
                    // Retrieve content and convert to String
                    Content content = originSpreadsheet.getContent(new Coordinate(row, col));
                    String stringContent = "";
                    if (content != null) {
                        stringContent = String.valueOf(content.getValue().getValue());
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

    private void createNewFile(String filename) throws IOException {
        File file = new File(filename);
        if (file.createNewFile()) {
            System.out.println("File created: " + file.getName());
        } else {
            System.out.println("File already exists.");
        }
    }

    public void SpreadsheetToS2V(String filename){}
}


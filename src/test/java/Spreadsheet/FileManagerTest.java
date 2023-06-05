package Spreadsheet;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class FileManagerTest {

    @Test
    void saveSpreadsheet() throws IOException {
        Spreadsheet ss = new Spreadsheet();
        ContentFactory cf = new ContentFactory();
        FileManager fm = new FileManager();
        String filepath = "mySpreadsheet.sv2";

        Content content1 = cf.createContent("124.3");
        Content content2 = cf.createContent("18.2");
        Content content3 = cf.createContent("foo");
        Content content4 = cf.createContent("1999");
        Content content5 = cf.createContent("bar");

        Coordinate coordinate1 = new Coordinate(0, 2);
        Coordinate coordinate2 = new Coordinate(2, 31);
        Coordinate coordinate3 = new Coordinate(13, 30);
        Coordinate coordinate4 = new Coordinate(3, 12);
        Coordinate coordinate5 = new Coordinate(45, 4);

        ss.updateContent(coordinate1, content1);
        ss.updateContent(coordinate2, content2);
        ss.updateContent(coordinate3, content3);
        ss.updateContent(coordinate4, content4);
        ss.updateContent(coordinate5, content5);


        fm.saveSpreadsheet(ss, filepath);
    }
}
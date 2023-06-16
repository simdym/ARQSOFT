package spreadsheet_project;

import edu.upc.etsetb.arqsoft.spreadsheet_project.Framework.FileManager;
import edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet.Content;
import edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet.ContentFactory;
import edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet.Coordinate;
import edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet.Spreadsheet;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FileManagerTest {

    @Test
    void testLoadAndSaveSpreadsheet() throws IOException {
        Spreadsheet ss1 = new Spreadsheet();
        FileManager fm = new FileManager();
        String filepath = "spreadsheetToBeloaded.sv2";

        Content content1 = ContentFactory.createContent("124.3");
        Content content2 = ContentFactory.createContent("18.2");
        Content content3 = ContentFactory.createContent("foo");
        Content content4 = ContentFactory.createContent("1999");
        Content content5 = ContentFactory.createContent("bar");

        Coordinate coordinate1 = new Coordinate(0, 2);
        Coordinate coordinate2 = new Coordinate(2, 31);
        Coordinate coordinate3 = new Coordinate(13, 30);
        Coordinate coordinate4 = new Coordinate(3, 12);
        Coordinate coordinate5 = new Coordinate(45, 4);

        ss1.updateContent(coordinate1, content1);
        ss1.updateContent(coordinate2, content2);
        ss1.updateContent(coordinate3, content3);
        ss1.updateContent(coordinate4, content4);
        ss1.updateContent(coordinate5, content5);

        Spreadsheet ss2 = new Spreadsheet();
        fm.saveSpreadsheet(ss1, filepath);
        fm.loadSpreadsheet(filepath, ss2);

        Object value1_ss1 = ss1.getContent(coordinate1).getValue().getValue();
        Object value1_ss2 = ss2.getContent(coordinate1).getValue().getValue();

        Object value2_ss1 = ss1.getContent(coordinate2).getValue().getValue();
        Object value2_ss2 = ss2.getContent(coordinate2).getValue().getValue();

        assertAll("Testing all cells remain the same",
                () -> assertEquals(ss1.getContent(coordinate1).getValue().getValue(), ss2.getContent(coordinate1).getValue().getValue()),
                () -> assertEquals(ss1.getContent(coordinate2).getValue().getValue(), ss2.getContent(coordinate2).getValue().getValue()),
                () -> assertEquals(ss1.getContent(coordinate3).getValue().getValue(), ss2.getContent(coordinate3).getValue().getValue()),
                () -> assertEquals(ss1.getContent(coordinate4).getValue().getValue(), ss2.getContent(coordinate4).getValue().getValue()),
                () -> assertEquals(ss1.getContent(coordinate5).getValue().getValue(), ss2.getContent(coordinate5).getValue().getValue())
        );
    }
}
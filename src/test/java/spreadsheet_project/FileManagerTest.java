package spreadsheet_project;

import edu.upc.etsetb.arqsoft.spreadsheet.entities.CircularDependencyException;
import edu.upc.etsetb.arqsoft.spreadsheet.entities.ContentException;
import edu.upc.etsetb.arqsoft.spreadsheet.usecases.marker.ReadingSpreadSheetException;
import edu.upc.etsetb.arqsoft.spreadsheet.usecases.marker.SavingSpreadSheetException;
import edu.upc.etsetb.arqsoft.spreadsheet_project.Framework.Controller;
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
    void testLoadAndSaveSpreadsheet() throws IOException, ContentException, ReadingSpreadSheetException, SavingSpreadSheetException, CircularDependencyException {
        Controller controller1 = new Controller();
        String filepath = "spreadsheetToBeloaded.sv2";

        String cellID1 = "A1";
        String cellID2 = "B1";
        String cellID3 = "C3";
        String cellID4 = "A3";
        String cellID5 = "A4";

        String value1 = "124.3";
        String value2 = "=MAX(2;1)";
        String value3 = "foo";
        String value4 = "=A1-2";
        String value5 = "=13-2";

        controller1.setCellContent(cellID1, value1);
        controller1.setCellContent(cellID2, value2);
        controller1.setCellContent(cellID3, value3);
        controller1.setCellContent(cellID4, value4);
        controller1.setCellContent(cellID5, value5);

        controller1.saveSpreadSheetToFile(filepath);

        Controller controller2 = new Controller();
        controller2.readSpreadSheetFromFile(filepath);

        System.out.println("Excepted vs reality: " + controller1.getCellContentAsString(cellID1) + " vs " + controller2.getCellContentAsString(cellID1));
        System.out.println("Excepted vs reality: " + controller1.getCellContentAsString(cellID2) + " vs " + controller2.getCellContentAsString(cellID2));
        System.out.println("Excepted vs reality: " + controller1.getCellContentAsString(cellID3) + " vs " + controller2.getCellContentAsString(cellID3));
        System.out.println("Excepted vs reality: " + controller1.getCellContentAsString(cellID4) + " vs " + controller2.getCellContentAsString(cellID4));
        System.out.println("Excepted vs reality: " + controller1.getCellContentAsString(cellID5) + " vs " + controller2.getCellContentAsString(cellID5));

        assertAll("Testing all cells remain the same",
                () -> assertEquals(controller1.getCellContentAsString(cellID1), controller2.getCellContentAsString(cellID1)),
                () -> assertEquals(controller1.getCellContentAsString(cellID2), controller2.getCellContentAsString(cellID2)),
                () -> assertEquals(controller1.getCellContentAsString(cellID3), controller2.getCellContentAsString(cellID3)),
                () -> assertEquals(controller1.getCellContentAsString(cellID4), controller2.getCellContentAsString(cellID4)),
                () -> assertEquals(controller1.getCellContentAsString(cellID5), controller2.getCellContentAsString(cellID5))
        );
    }
}
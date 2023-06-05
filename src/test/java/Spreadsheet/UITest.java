package Spreadsheet;

import Spreadsheet.Cmd.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class UITest {

    @Test
    void testRFCmd() throws FileNotFoundException {
        // TODO

        String commandType = "RF";
        String filepath = "src/test/java/Spreadsheet/command_file.txt";
        String commandStr = commandType + " " + filepath;

        ByteArrayInputStream in = new ByteArrayInputStream(commandStr.getBytes());
        System.setIn(in);

        UI ui = new UI();
        RFCmd testCmd = (RFCmd) ui.askForCmd();

        System.out.println("Expected: " + commandStr);
        System.out.println("Actual: " + testCmd.getType());// + " " + testCmd.getFilepath());

        assertAll("Testing all variables",
                () -> assertEquals(commandType, testCmd.getType())
                //() -> assertEquals(filepath, testCmd.getFilepath())
        );
    }

    @Test
    void testCCmd() throws FileNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream("C".getBytes());
        System.setIn(in);

        UI ui = new UI();
        Cmd testCmd = ui.askForCmd();

        System.out.println("Expected: C");
        System.out.println("Actual: " + testCmd.getType());

        assertEquals("C", testCmd.getType());
    }

    @Test
    void testECmd() throws FileNotFoundException {
        String commandType = "E";
        String cellId = "B3";
        String contentStr = "123";
        String commandStr = commandType + " " + cellId + " " + contentStr;

        ByteArrayInputStream in = new ByteArrayInputStream(commandStr.getBytes());
        System.setIn(in);

        UI ui = new UI();
        ECmd testCmd = (ECmd) ui.askForCmd();

        System.out.println("Expected: " + commandStr);
        System.out.println("Actual: " + testCmd.getType() + " " + testCmd.getCellID() + " " + testCmd.getContentStr());

        assertAll("Testing all variables",
                () -> assertEquals(commandType, testCmd.getType()),
                () -> assertEquals(cellId, testCmd.getCellID()),
                () -> assertEquals(contentStr, testCmd.getContentStr())
        );
    }

    @Test
    void testLCmd() throws FileNotFoundException {
        String commandType = "L";
        String filepath = "spreadsheet_path.sv2";
        String commandStr = commandType + " " + filepath;

        ByteArrayInputStream in = new ByteArrayInputStream(commandStr.getBytes());
        System.setIn(in);

        UI ui = new UI();
        LCmd testCmd = (LCmd) ui.askForCmd();

        System.out.println("Expected: " + commandStr);
        System.out.println("Actual: " + testCmd.getType() + " " + testCmd.getFilepath());

        assertAll("Testing all variables",
                () -> assertEquals(commandType, testCmd.getType()),
                () -> assertEquals(filepath, testCmd.getFilepath())
        );
    }

    @Test
    void testSCmd() throws FileNotFoundException {
        String commandType = "S";
        String filepath = "spreadsheet_path.sv2";
        String commandStr = commandType + " " + filepath;

        ByteArrayInputStream in = new ByteArrayInputStream(commandStr.getBytes());
        System.setIn(in);

        UI ui = new UI();
        SCmd testCmd = (SCmd) ui.askForCmd();

        System.out.println("Expected: " + commandStr);
        System.out.println("Actual: " + testCmd.getType() + " " + testCmd.getFilepath());

        assertAll("Testing all variables",
                () -> assertEquals(commandType, testCmd.getType()),
                () -> assertEquals(filepath, testCmd.getFilepath())
        );
    }
}
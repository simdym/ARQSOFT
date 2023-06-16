package spreadsheet_project;

import edu.upc.etsetb.arqsoft.spreadsheet_project.Cmd.Cmd;
import edu.upc.etsetb.arqsoft.spreadsheet_project.Cmd.CmdType;
import edu.upc.etsetb.arqsoft.spreadsheet_project.Framework.UI;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class UITest {

    @Test
    void testRFCmd() throws FileNotFoundException {
        String commandType = "RF";
        String filepath = "src/test/java/Spreadsheet/command_file.txt";
        String commandStr = commandType + " " + filepath;

        ByteArrayInputStream in = new ByteArrayInputStream(commandStr.getBytes());
        System.setIn(in);

        UI ui = new UI();
        Cmd testCmd = ui.askForCmd();

        System.out.println("Expected: " + commandStr);
        System.out.println("Actual: " + testCmd.getCmdType());// + " " + testCmd.getFilepath());

        assertAll("Testing all variables",
                () -> assertEquals(CmdType.RUN_FROM_FILE, testCmd.getCmdType()),
                () -> assertEquals(filepath, testCmd.getArgument(0))
        );
    }

    @Test
    void testCCmd() throws FileNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream("C".getBytes());
        System.setIn(in);

        UI ui = new UI();
        Cmd testCmd = ui.askForCmd();

        System.out.println("Expected: "+ CmdType.CREATE_SPREADSHEET);
        System.out.println("Actual: " + testCmd.getCmdType());

        assertEquals(CmdType.CREATE_SPREADSHEET, testCmd.getCmdType());
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
        Cmd testCmd = ui.askForCmd();

        System.out.println("Expected: " + commandStr);
        System.out.println("Actual: " + testCmd.getCmdType() + " " + testCmd.getArgument(0) + " " + testCmd.getArgument(1));

        assertAll("Testing all variables",
                () -> assertEquals(CmdType.EDIT_CELL, testCmd.getCmdType()),
                () -> assertEquals(cellId, testCmd.getArgument(0)),
                () -> assertEquals(contentStr, testCmd.getArgument(1))
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
        Cmd testCmd = ui.askForCmd();

        System.out.println("Expected: " + commandStr);
        System.out.println("Actual: " + testCmd.getCmdType() + " " + testCmd.getArgument(0));

        assertAll("Testing all variables",
                () -> assertEquals(CmdType.LOAD_SPREADSHEET, testCmd.getCmdType()),
                () -> assertEquals(filepath, testCmd.getArgument(0))
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
        Cmd testCmd = ui.askForCmd();

        System.out.println("Expected: " + commandStr);
        System.out.println("Actual: " + testCmd.getCmdType() + " " + testCmd.getArgument(0));

        assertAll("Testing all variables",
                () -> assertEquals(CmdType.SAVE_SPREADSHEET, testCmd.getCmdType()),
                () -> assertEquals(filepath, testCmd.getArgument(0))
        );
    }
}
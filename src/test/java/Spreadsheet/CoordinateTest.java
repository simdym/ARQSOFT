package Spreadsheet;

import Spreadsheet.Exceptions.InvalidCellIDException;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CoordinateTest {

    @Test
    void testGetRow() {
        Coordinate testCoor = new Coordinate(12, 34);
        assertEquals(testCoor.getRow(), 12);
    }

    @Test
    void testGetCol() {
        Coordinate testCoor = new Coordinate(12, 34);
        assertEquals(testCoor.getCol(), 34);
    }

    @Test
    void testCellIdToCoordinateRow() {
        Coordinate testCoor = new Coordinate("D15");
        assertEquals(testCoor.getRow(), 14);
    }

    @Test
    void testCellIdToCoordinateColumn() {
        Coordinate testCoor = new Coordinate("D15");
        assertEquals(testCoor.getCol(), 3);
    }

    @Test
    void testMultiCharCellIdToCoordinateColumn() {
        Coordinate testCoor = new Coordinate("AD15");
        assertEquals(testCoor.getCol(), 29);
    }

    @Test
    void testCellIdStartingWithDigitThrows() {
        String cellId = "15AR";

        Exception exception = assertThrows(InvalidCellIDException.class, () -> {
            new Coordinate(cellId);
        });

        String expectedMessage = "Invalid coordinate format: " + cellId;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testCellIdWithCharsAfterDigitThrows() {
        String cellId = "AR23A";

        Exception exception = assertThrows(InvalidCellIDException.class, () -> {
            new Coordinate(cellId);
        });

        String expectedMessage = "Invalid coordinate format: " + cellId;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
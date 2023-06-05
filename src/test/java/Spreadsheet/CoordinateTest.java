package Spreadsheet;

import Spreadsheet.Exceptions.InvalidCellIDException;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CoordinateTest {
    @Test
    void testNotEquals() {
        Coordinate coordinate1 = new Coordinate(12, 34);
        Coordinate coordinate2 = new Coordinate(56, 78);
        assertNotEquals(coordinate1, coordinate2);
    }

    @Test
    void testEquals() {
        Coordinate coordinate1 = new Coordinate(12, 34);
        Coordinate coordinate2 = new Coordinate(12, 34);
        assertEquals(coordinate1, coordinate2);
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
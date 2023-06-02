package Spreadsheet;

import org.junit.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

class CoordinateTest {

    @Test
    void testGetX() {
        Coordinate testCoor = new Coordinate(12, 34);
        Assert.assertEquals(testCoor.getX(), 12);
    }

    @Test
    void testGetY() {
        Coordinate testCoor = new Coordinate(12, 34);
        Assert.assertEquals(testCoor.getY(), 34);
    }
}
package Spreadsheet;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SpreadsheetTest {

    @Test
    void updateContent_numericalToEmptyCell() {
        Spreadsheet ss = new Spreadsheet();

        NumericalContent newContent = new NumericalContent("123.23");
        Coordinate coordinate = new Coordinate(2, 3);

        ss.updateContent(coordinate, newContent);
        Content updatedContent = ss.getContent(coordinate);


        Value newValueWrapper = newContent.getValue();
        Value updatedValueWrapper = updatedContent.getValue();

        Object newValue = newValueWrapper.getValue();
        Object updatedValue = updatedValueWrapper.getValue();

        System.out.println("newValue: " + newValue);
        System.out.println("updatedValue: " + updatedValue);

        assertEquals(updatedContent.getValue().getValue(), newContent.getValue().getValue());
    }

    @Test
    void updateContent_numericalToOccupiedCell() {
        Spreadsheet ss = new Spreadsheet();

        NumericalContent existingContent = new NumericalContent("3.14159");
        NumericalContent newContent = new NumericalContent("2.71828");
        Coordinate coordinate = new Coordinate(2, 3);

        ss.updateContent(coordinate, existingContent);
        ss.updateContent(coordinate, newContent);
        Content updatedContent = ss.getContent(coordinate);

        Value newValueWrapper = newContent.getValue();
        Value updatedValueWrapper = updatedContent.getValue();

        Object newValue = newValueWrapper.getValue();
        Object updatedValue = updatedValueWrapper.getValue();

        System.out.println("newValue: " + newValue);
        System.out.println("updatedValue: " + updatedValue);

        assertEquals(updatedContent.getValue().getValue(), newContent.getValue().getValue());
    }

    @Test
    void updateContent_textToEmptyCell() {
        Spreadsheet ss = new Spreadsheet();

        TextContent newContent = new TextContent("Hello world");
        Coordinate coordinate = new Coordinate(2, 3);

        ss.updateContent(coordinate, newContent);
        Content updatedContent = ss.getContent(coordinate);


        Value newValueWrapper = newContent.getValue();
        Value updatedValueWrapper = updatedContent.getValue();

        Object newValue = newValueWrapper.getValue();
        Object updatedValue = updatedValueWrapper.getValue();

        System.out.println("newValue: " + newValue);
        System.out.println("updatedValue: " + updatedValue);

        assertEquals(updatedContent.getValue().getValue(), newContent.getValue().getValue());
    }

    @Test
    void updateContent_textToOccupiedCell() {
        Spreadsheet ss = new Spreadsheet();

        TextContent existingContent = new TextContent("foo");
        TextContent newContent = new TextContent("bar");
        Coordinate coordinate = new Coordinate(2, 3);

        ss.updateContent(coordinate, existingContent);
        ss.updateContent(coordinate, newContent);
        Content updatedContent = ss.getContent(coordinate);

        Value newValueWrapper = newContent.getValue();
        Value updatedValueWrapper = updatedContent.getValue();

        Object newValue = newValueWrapper.getValue();
        Object updatedValue = updatedValueWrapper.getValue();

        System.out.println("newValue: " + newValue);
        System.out.println("updatedValue: " + updatedValue);

        assertEquals(updatedContent.getValue().getValue(), newContent.getValue().getValue());
    }

    @Test
    void getContent() {
    }
}
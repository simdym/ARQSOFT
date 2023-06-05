package Spreadsheet.Exceptions;

public class CommandFormatException extends IllegalArgumentException {
    public CommandFormatException(String message) {
        super(message);
    }
}

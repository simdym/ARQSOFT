package Spreadsheet.Exceptions;

public class InvalidCellIDException extends IllegalArgumentException {
    public InvalidCellIDException(String message) {
        super(message);
    }
}

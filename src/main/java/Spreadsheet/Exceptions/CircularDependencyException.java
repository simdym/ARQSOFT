package Spreadsheet.Exceptions;

public class CircularDependencyException extends IllegalArgumentException {
    public CircularDependencyException(String message) {
        super(message);
    }
}

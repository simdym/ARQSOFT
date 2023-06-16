package edu.upc.etsetb.arqsoft.spreadsheet_project.Exceptions;

public class InvalidCellIDException extends IllegalArgumentException {
    public InvalidCellIDException(String message) {
        super(message);
    }
}

package edu.upc.etsetb.arqsoft.spreadsheet_project.Exceptions;

public class CircularDependencyException extends IllegalArgumentException {
    public CircularDependencyException(String message) {
        super(message);
    }
}

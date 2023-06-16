package edu.upc.etsetb.arqsoft.spreadsheet_project.Formula;

/**
 *
 * Interface which extends FormulaComponent and is implemented by Cell, Function and NumericalValue
 *
 * Has method getDoubleValue which is implemented by each of the subclasses and returns a double value.
 */
public interface Operand extends FormulaComponent {

     double getDoubleValue();


}

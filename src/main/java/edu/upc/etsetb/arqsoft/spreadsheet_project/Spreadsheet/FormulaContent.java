package edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet;

import edu.upc.etsetb.arqsoft.spreadsheet_project.Formula.Tokenizer;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;


/**
 * Formula Content of a cell, extends abstract class Content
 * FormulaContent gas the content of the cell as a string and the value of the cell as a numerical value,
 * as well as a list of cells that the formula depends on and the postfix expression of the formula.
 *
 */
public class FormulaContent extends Content {

        private List<Cell> dependentCells; //list of cells that the formula depends on
        private LinkedList<Tokenizer.Token> postfixExpression;//postfix expression of the formula, saved so that it does not have to be re-calculated
        public FormulaContent(String formulaStr){
            super(formulaStr, null);
            this.content = formulaStr;
            this.dependentCells = new LinkedList<Cell>();
            this.postfixExpression = new LinkedList<Tokenizer.Token>();
        }

        @Override
        public String getSaveableString() {//To save correctly the formula, the semicolons are replaced by commas
            String toBeSaved = getContent();
            return toBeSaved.replaceAll(";", ",");
        }

        public void setValue(NumericalValue value) {
            super.value = value;
        }

        public List<Cell> getDependentCells() {
            return dependentCells;
        }

        public void setDependentCells(List<Cell> dependentCells) {
            this.dependentCells = new ArrayList<Cell>(dependentCells);
        }

        public LinkedList<Tokenizer.Token> getPostfixExpression() {
            return this.postfixExpression;
        }


        public void setPostfixExpression(LinkedList<Tokenizer.Token> postfixExpression) {
            this.postfixExpression = new LinkedList<Tokenizer.Token>(postfixExpression);
        }

        public List<Cell> getDependentCellsList() {
            return this.dependentCells;
        }
        public void setDependentCellsList(List<Cell> dependentCellsParsed){
            this.dependentCells = dependentCellsParsed ;
        }


}


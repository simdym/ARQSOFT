package edu.upc.etsetb.arqsoft.spreadsheet_project.Spreadsheet;

import edu.upc.etsetb.arqsoft.spreadsheet_project.Formula.Tokenizer;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
public class FormulaContent extends Content {

        private List<Cell> dependentCells;
        private LinkedList<Tokenizer.Token> postfixExpression;
        public FormulaContent(String formulaStr){
            super(formulaStr, new TextValue(formulaStr));
            this.content = formulaStr;
            this.dependentCells = new LinkedList<Cell>();
            this.postfixExpression = new LinkedList<Tokenizer.Token>();
        }

        @Override
        public String getSaveableString() {
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


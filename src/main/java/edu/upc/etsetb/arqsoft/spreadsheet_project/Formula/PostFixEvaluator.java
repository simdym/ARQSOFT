package edu.upc.etsetb.arqsoft.spreadsheet_project.Formula;
import java.util.Stack;
import java.util.LinkedList;

import edu.upc.etsetb.arqsoft.spreadsheet_project.Exceptions.EvaluationException;


public class PostFixEvaluator {
    public PostFixEvaluator() {}
    //has to have a spreadsheet evaluator
    public double evaluatePostfix(LinkedList<FormulaComponent> postFixExpression) {

            Stack<Double> operandStack = new Stack<>();
            for (FormulaComponent fComp : postFixExpression){
                if(fComp instanceof Operand){
                    double operandValue = ((Operand) fComp).getDoubleValue();
                    operandStack.push(operandValue);
                }
                if (fComp instanceof Operator){
                    double operator2 = operandStack.pop();
                    double operator1 = operandStack.pop();
                    double operationResult = ((Operator) fComp).evaluateOperation(operator1,operator2);
                    operandStack.push(operationResult);
                }
            }
            double result = operandStack.pop();
        if (!operandStack.isEmpty()) {
            throw new EvaluationException("Evaluation error: Operand stack is not empty");
        }
        return result;
        }

    }
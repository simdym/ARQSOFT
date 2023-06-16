package edu.upc.etsetb.arqsoft.spreadsheet_project.Formula;
import java.util.Stack;
import java.util.LinkedList;

import edu.upc.etsetb.arqsoft.spreadsheet_project.Exceptions.EvaluationException;

/**
 * Class which evaluates a postfix expression. It has a method evaluatePostfix which takes a LinkedList of FormulaComponents and returns a double value.
 */
public class PostFixEvaluator {
    public PostFixEvaluator() {}
    //has to have a spreadsheet evaluator
    public double evaluatePostfix(LinkedList<FormulaComponent> postFixExpression) throws EvaluationException {

            Stack<Double> operandStack = new Stack<>();//Creates a stack of operands
            for (FormulaComponent fComp : postFixExpression){
                if(fComp instanceof Operand){
                    double operandValue = ((Operand) fComp).getDoubleValue(); //if fComp is an operand, it gets its double value (computed depending on the subclass)
                    operandStack.push(operandValue);
                }
                if (fComp instanceof Operator){// if fComp is an operator, it pops the two operands from the stack and evaluates the operation in the adequate prder
                    double operator2 = operandStack.pop();
                    double operator1 = operandStack.pop();
                    double operationResult = ((Operator) fComp).evaluateOperation(operator1,operator2);
                    operandStack.push(operationResult);
                }
            }
            double result = operandStack.pop();//pops the result (last value on the stack)

            if (!operandStack.isEmpty()) {//Throws exception if the stack is not empty
                throw new EvaluationException("Evaluation error: Operand stack is not empty");
            }
            return result;
        }

    }
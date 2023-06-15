package Spreadsheet;
import java.util.Stack;
import java.util.LinkedList;
import Spreadsheet.Tokenizer;
import Spreadsheet.Operand;
import java.util.Collections;


public class PostFixEvaluator {
    public PostFixEvaluator() {}
    //has to have a spreadsheet evaluator
    public double evaluatePostfix(LinkedList<FormulaComponent> postFixExpression, Spreadsheet spreadsheet) {

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
            // throw exception for non-empty stack
        }
            return result;
    }}
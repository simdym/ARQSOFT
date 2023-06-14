package Spreadsheet;
import java.util.Stack;
import java.util.LinkedList;
import Spreadsheet.Tokenizer;
import Spreadsheet.Operand;
import java.util.Collections;


public class PostFixEvaluator {
    public PostFixEvaluator() {}
    //has to have a spreadsheet evaluator
    public float evaluatePostfix(LinkedList<FormulaComponent> postFixExpression) {

            Stack<Float> operandStack = new Stack<>();
            for (FormulaComponent fComp : postFixExpression){
                if(fComp instanceof Operand){
                    //float operandValue = fComp.getValue();
                    //operandStack.push(operandValue);

                }
                if (fComp instanceof Operator){
                    float operator2 = operandStack.pop();
                    float operator1 = operandStack.pop();
                    float operationResult = ((Operator) fComp).evaluateOperation(operator1,operator2);
                    operandStack.push(operationResult);
                }

            }
            float result = operandStack.pop();
        if (!operandStack.isEmpty()) {
            // throw exception for non-empty stack
        }
            return result;
    }}
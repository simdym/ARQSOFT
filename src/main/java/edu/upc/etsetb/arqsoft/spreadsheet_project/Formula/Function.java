package edu.upc.etsetb.arqsoft.spreadsheet_project.Formula;

import java.util.LinkedList;
/**
* Class function, which can implement argument and operand.
 * It contains a list of arguments, and a method to add arguments to the list.
 *
 *Static class functionFactory to create new Function objects Sum(), Min(), etc. Depending on the string received.
 *
 * abstract double getDoubleValue which is implemented by each of the function subclasses: Sum, Min, Max and Average.
 **/
public abstract class Function implements Argument, Operand {
    protected LinkedList<Argument> arguments;

    public Function() {
        arguments = new LinkedList<>(); //constructor
    } //constructor

    public void addArgument(Argument argument) { //method that adds one argument to the list
        arguments.add(argument);
    }


    public abstract double getDoubleValue();//abstract, since it depends on each subclass of funcion

    public static class FunctionFactory {//class functionfactory with static method createfunction which receives string (SUM,etc) and creates an object suma, an object min...
        public static Function createFunction(String functionName) {
            switch (functionName) {
                case "SUMA(":
                    return new Sum();
                case "MIN(":
                    return new Min();
                case "AVG(":
                    return new Average();
                case "MAX(":
                    return new Max();
                default:
                    throw new IllegalArgumentException("Invalid function name: " + functionName);
            }
        }

    }
}

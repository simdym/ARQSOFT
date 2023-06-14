package Spreadsheet;

import java.util.LinkedList;

public abstract class Function implements Argument {
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
                case "SUM(":
                    return new Sum();
                case "MIN(":
                    return new Min();
                case "AVG(":
                    return new Average();
                case "MAX(":
                    return new Max();
                // Add more cases for other functions
                default:
                    throw new IllegalArgumentException("Invalid function name: " + functionName);
            }
        }

    }
}

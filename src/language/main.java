package language;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

/**
 * Created by mrowland on 11/15/2017 for FPSLang.
 */

public class main {
    private static Stack<Integer> operatingStack = new Stack<>();
    private static LanguageMap languageMap;

    public static void main (String[] args) {
        languageMap = new LanguageMap();

        String filepath = "sourceFile.txt";

        Scanner sourceFile = null;
        try {
            sourceFile = new Scanner(new File(filepath));
        } catch (FileNotFoundException exception) {
            System.out.println("File not found. " + exception);
        }

        assert (sourceFile != null);

        LanguageParser parser = new LanguageParser(sourceFile);

        while (parser.hasNextToken()) {
            String currentToken = parser.getNextToken();
            if (languageMap.getValueForMapKey(currentToken).equals("comment")) {
                while (parser.hasNextToken() && !parser.getNextToken().equals("endComment"))
                    continue;
                continue;
            }
            else if (languageMap.getValueForMapKey(currentToken).equals("newVar")) {
                String varName = parser.getNextToken();
                String varData = parser.getNextToken();
                addVariable(varName, varData);
            }
            else if (languageMap.getValueForMapKey(currentToken).equals("updateVar")) {
                //variable must be able to do math with itself, and then be updated and stored
                //use local public method "updateVariable(String varName, String value)" to update
            }
            else //might need something else here
                evaluateNextOperation(currentToken);

        }

    }

    public static Integer testIfStringIsInteger (String str) {
        Integer intTest = null;
        try {
            intTest = new Integer(str);
        } catch (NumberFormatException exception) {}
        return intTest;
    }

    /*
        evaluateNextOperation receives current keyword from main, and will push to the stack if the current key is an integer, else
     */
    public static void evaluateNextOperation(String currentToken) {

        Integer integer = testIfStringIsInteger(currentToken);

        if (integer != null) {
            operatingStack.push(integer);
        } else {

            switch (languageMap.getValueForMapKey(currentToken)) {

                case "add":
                    if (operatingStack.size() <= 2)
                        System.out.println("Must be enough Integers on the stack");
                    else {
                        int firstPop = operatingStack.pop();
                        int secondPop = operatingStack.pop();
                        operatingStack.add(secondPop + firstPop);
                    }
                    break;

                case "subtract":
                    if (operatingStack.size() <= 2)
                        System.out.println("Must be enough Integers on the stack");
                    else {
                        int firstPop = operatingStack.pop();
                        int secondPop = operatingStack.pop();
                        operatingStack.add(secondPop - firstPop);
                    }
                    break;

                case "multiply":
                    if (operatingStack.size() <= 2)
                        System.out.println("Must be enough Integers on the stack");
                    else {
                        int firstPop = operatingStack.pop();
                        int secondPop = operatingStack.pop();
                        operatingStack.add(secondPop * firstPop);
                    }
                    break;

                case "divide":
                    if (operatingStack.size() <= 2)
                        System.out.println("Must be enough Integers on the stack");
                    else {
                        int firstPop = operatingStack.pop();
                        int secondPop = operatingStack.pop();
                        operatingStack.add(secondPop / firstPop);
                    }
                    break;

                case "power":
                    if (operatingStack.size() <= 2)
                        System.out.println("Must be enough Integers on the stack");
                    else {
                        int firstPop = operatingStack.pop();
                        int secondPop = operatingStack.pop();
                        operatingStack.add((int) Math.pow(secondPop, firstPop));
                    }
                    break;
                case "print":
                    if (operatingStack.isEmpty())
                        System.out.println("There is nothing on the stack");
                    else {
                        System.out.println(operatingStack.pop());
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Syntax error. Keyword or Variable \"" + currentToken + "\" is undefined");
            }
        }
    }

    public static void addVariable(String varName, String value) {
        languageMap.addToVariableMap(varName, value);
    }

    public static void updateVariable(String varName, String value){
        languageMap.updateVariableInVariableMap(varName, value);
    }
}

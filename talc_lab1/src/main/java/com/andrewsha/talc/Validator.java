package com.andrewsha.talc;

public class Validator {

    public static boolean isValid(String expr) {
        int i = 0;
        int numOfOpBrackets = 0;
        int numOfDots = 0;

        while (i < expr.length()) {
            char c = expr.charAt(i);

            if (!Character.isDigit(c) && !App.operations.containsKey(c + "") && c != ')'
                    && c != '.' && expr.indexOf("log(") != i) {
                System.out.println("Incorrect expression: invalid symbol " + c);
                return false;
            }

            if (c == '(') {
                numOfDots = 0;
                if (!Utils.isNumeric(expr.charAt(i + 1) + "") && expr.charAt(i + 1) != '.'
                        && expr.charAt(i + 1) != '(' && expr.charAt(i + 1) != 'l') {
                    System.out.println(
                            "Incorrect expression: no digit found after opening bracket "
                                    + expr.charAt(i + 1));
                    return false;
                }
                numOfOpBrackets++;
            }

            else if (c == ')') {
                numOfDots = 0;
                if (numOfOpBrackets == 0) {
                    System.out.println(
                            "Incorrect expression: mismatching number of brackets "
                                    + expr);
                    return false;
                }
                numOfOpBrackets--;
            }

            else if (App.operations.containsKey(c + "")) {
                numOfDots = 0;
                if ((!Utils.isNumeric(expr.charAt(i - 1) + "")
                        && expr.charAt(i - 1) != ')' && expr.charAt(i - 1) != '.')
                        || (!Utils.isNumeric(expr.charAt(i + 1) + "")
                                && expr.charAt(i + 1) != '(' && expr.charAt(i + 1) != '.')
                                && expr.charAt(i + 1) != 'l') {
                    System.out.println(
                            "Incorrect expression: no operands matching operation");
                    return false;
                }
            }

            else if (Character.isDigit(c) || c == '.') {

                if (c == '.')
                    numOfDots++;
                if (numOfDots > 1) {
                    System.out.println("Incorrect expression: too many dots");
                    return false;
                }

            }

            else if (expr.indexOf("log(") == i) {
                numOfDots = 0;
                int numOfLogBrackets = 1;
                int itemp = i + 5;
                int endOfLog = 0;
                int commaIndex = 0;

                while (numOfLogBrackets != 0 && itemp < expr.length()) {
                    if (expr.charAt(itemp) == '(')
                        numOfLogBrackets++;
                    else if (expr.charAt(itemp) == ')')
                        numOfLogBrackets--;

                    if (expr.charAt(itemp) == ',' && numOfLogBrackets == 1)
                        commaIndex = itemp;

                    itemp++;
                }

                if (numOfLogBrackets == 0)
                    endOfLog = itemp - 1;
                else {
                    System.out.println("Incorrect log function: no closing bracket");
                    return false;
                }

                if (commaIndex == 0) {
                    System.out.println(
                            "Incorrect log function: no comma found in brackets");
                    return false;
                }

                String firstOp = expr.substring(i + 4, commaIndex);
                String secondOp = expr.substring(commaIndex + 1, endOfLog);

                System.out.println("First " + firstOp + "; Second " + secondOp);

                if (!isValid(firstOp) || !isValid(secondOp))
                    return false;
                i = endOfLog;
            }
            i++;
        }

        if (numOfOpBrackets > 0) {
            System.out.println("Incorrect expression: mismatching number of brackets");
            return false;
        }
        return true;
    }
}

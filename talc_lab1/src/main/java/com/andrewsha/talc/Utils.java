package com.andrewsha.talc;

import java.util.Stack;

public class Utils {

    public static String prepareSource(String source) {
        source = source.replaceAll(" ", "").replaceAll("-", "+0-")
                .replaceAll("\\(\\+0-", "\\(\\0-").replaceAll("\\,\\+0-", "\\,\\0-")
                .replaceAll("([\\d\\)])(\\()", "$1*$2")
                .replaceAll("(\\d)([a-z])", "$1*$2");
        if (source.charAt(0) == '+') {
            source = source.substring(1);
        }
        return source;
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static Stack<String> reverseStack(Stack<String> stack) {
        Stack<String> result = new Stack<>();
        while (stack.size() > 0)
            result.push(stack.pop());
        return result;
    }

    public static Stack<String> toPostfix(String line) {
        Stack<String> stack = new Stack<>();
        Stack<String> postfix = new Stack<>();

        char[] expr = line.toCharArray();

        for (int i = 0; i < expr.length; i++) {
            char c = expr[i];
            if (Character.isDigit(c) || c == '.') {
                String num = "";
                while (i < expr.length && !App.operations.containsKey(expr[i] + "")
                        && expr[i] != ')' && expr[i] != 'l') {
                    if (isNumeric(String.valueOf(expr[i])) || expr[i] == '.') {
                        num += expr[i];
                        i++;
                    }
                }
                i--;
                postfix.push(num);
            }

            else if (c == '(') {
                stack.push(c + "");
            }

            else if (c == ')') {
                // insert into output expression everything before '('
                while (stack.size() > 0 && !stack.peek().equals("("))
                    postfix.push(String.valueOf(stack.pop()));
                stack.pop();
            }

            else if (App.operations.containsKey(c + "")) {
                while (stack.size() > 0
                        && App.operations.get(stack.peek()) >= App.operations.get(c + ""))
                    postfix.push(String.valueOf(stack.pop()));

                stack.push(c + "");
            }

            else if (c == 'l') {
                int numOfBrackets = 1;
                int itemp = i + 5;
                int endOfLog = 0;
                int commaIndex = 0;

                while (numOfBrackets != 0 && itemp < line.length()) {
                    if (line.charAt(itemp) == '(')
                        numOfBrackets++;
                    else if (line.charAt(itemp) == ')')
                        numOfBrackets--;
                    if (line.charAt(itemp) == ',' && numOfBrackets == 1)
                        commaIndex = itemp;

                    itemp++;
                }

                if (numOfBrackets == 0)
                    endOfLog = itemp - 1;

                String firstOp = line.substring(i + 4, commaIndex);
                String secondOp = line.substring(commaIndex + 1, endOfLog);

                postfix.push(Calculator.calcPostfix(toPostfix(firstOp)) + "");
                postfix.push(Calculator.calcPostfix(toPostfix(secondOp)) + "");

                while (stack.size() > 0
                        && App.operations.get(stack.peek()) >= App.operations.get("log"))
                    postfix.push(String.valueOf(stack.pop()));

                stack.push("log");
                i = endOfLog;
            }
        }

        while (stack.size() > 0)
            postfix.push(String.valueOf(stack.pop()));

        return postfix;
    }
}

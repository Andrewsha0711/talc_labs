package com.andrewsha.talc;

import java.util.Stack;

public class Calculator {

    public static Double calc(String operation, double operand1, double operand2) {
        double ans;
        if (operation.equals("+"))
            ans = operand1 + operand2;
        else if (operation.equals("-"))
            ans = operand1 - operand2;
        else if (operation.equals("*"))
            ans = operand1 * operand2;
        else if (operation.equals("/"))
            ans = operand1 / operand2;
        else if (operation.equals("^"))
            ans = Math.pow(operand1, operand2);
        else if (operation.equals("log"))
            ans = Math.log(operand1) / Math.log(operand2);
        else
            return null;
        return ans;
    }

    public static double calcPostfix(Stack<String> postfix) {
        Stack<Double> operands = new Stack<>();
        int counter = 0;

        postfix = Utils.reverseStack(postfix);
        while (postfix.size() > 0) {

            String c = postfix.pop();
            if (Utils.isNumeric(c)) {
                operands.push(Double.parseDouble(c));
            }

            // take 2 last numbers from stack
            else if (App.operations.containsKey(c)) {
                double second = operands.size() > 0 ? operands.pop() : 0;
                double first = operands.size() > 0 ? operands.pop() : 0;

                operands.push(calc(c, first, second));
                counter++;

                if (c.equals("log"))
                    System.out.println(counter + ". log(" + first + ", " + second + ") = "
                            + operands.peek());
                else
                    System.out.println(counter + ". " + first + " " + c + " " + second
                            + " = " + operands.peek());
            }
        }
        return operands.pop();
    }
}

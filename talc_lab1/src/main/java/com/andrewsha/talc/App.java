package com.andrewsha.talc;

import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class App {
    public final static Map<String, Integer> operations =
            Map.of("(", 0, "+", 1, "-", 1, "*", 2, "/", 2, "^", 3, "log", 4);

    public static void main(String[] args) {
        System.out.println("Enter an expression");
        Scanner sc = new Scanner(System.in);
        String expr = sc.nextLine();

        expr = Utils.prepareSource(expr);

        if (Validator.isValid(expr)) {

            Stack<String> postfix = Utils.toPostfix(expr);
            if (postfix != null) {
                System.out.println("Postfix form: " + postfix);
                System.out
                        .println("Calculated answer:" + Calculator.calcPostfix(postfix));
            }
        }
        sc.close();
    }
}

package com.stockpenguin.materialcalculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Compiler {
    public static int compile(String input) {
        String postfix = "";
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (isOp(c)) {
                if (stack.isEmpty()) {
                    stack.push(c);
                } else if (isGreaterThan(c, stack.peek())){
                    stack.push(c);
                } else if (isLessThan(c, stack.peek())) {
                    ArrayList<Character> temp = new ArrayList<>();

                    while (isLessThan(c, stack.peek())) {
                        temp.add(stack.pop());
                    }

                    stack.push(c);
                    Collections.reverse(temp);

                    for (Character tempC : temp) {
                        stack.push(tempC);
                    }
                }
            }
        }
    }

    public static boolean isLessThan(char op1, char op2) {
        switch (op1) {
            case '+':
            case '-':
                switch (op2) {
                    case '+':
                    case '-':
                        return false;
                    case '*':
                    case '/':
                        return true;
                }
            case '*':
            case '/':
                switch (op2) {
                    case '*':
                    case '/':
                        return false;
                    case '+':
                    case '-':
                        return true;
                }
            default: return false;
        }
    }

    public static boolean isGreaterThan(char op1, char op2) {
        switch (op1) {
            case '+':
                switch (op2) {
                    case '+':
                        return true;
                    case '-':
                    case '*':
                    case '/':
                        return false;
                }
            case '-':
                switch (op2) {
                    case '-':
                        return true;
                    case '+':
                    case '*':
                    case '/':
                        return false;
                }
            case '*':
                switch (op2) {
                    case '+':
                    case '-':
                    case '*':
                        return true;
                    case '/':
                        return false;
                }

            case '/':
                switch (op2) {
                    case '+':
                    case '-':
                    case'/':
                        return true;
                    case '*':
                        return false;
                }
            default:
                return false;
        }
    }

    public static boolean isOp(char c) {
        switch (c) {
            case '+':
            case '-':
            case '*':
            case '/':
                return true;
            default:
                return false;
        }
    }
}

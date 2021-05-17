package com.stockpenguin.materialcalculator;

import java.util.Stack;

public class Compiler {
    private static Compiler instance;

    private Compiler() {}

    public static Compiler getInstance() {
        if (instance == null) {
            instance = new Compiler();
        }
        return instance;
    }

    public String infixToPostfix(String infix) {
        StringBuilder postfix = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);

            if (!isOp(c)) {
                /* add support for multiple digits */
                postfix.append(c);
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(stack.pop());
                }
                stack.pop();
            } else {
                while (!stack.isEmpty() && opPrec(c) <= opPrec(stack.peek())) {
                    postfix.append(stack.pop());
                }
                stack.push(c);
            }
        }
        while (!stack.isEmpty()) {
            if (stack.peek() == '(') {
                /* throw syntax error */
            }
            postfix.append(stack.pop());
        }
        return postfix.toString();
    }

    /* checks if a character is an operator */
    private boolean isOp(char c) {
        switch (c) {
            case '+':
            case '-':
            case '*':
            case '/':
            case '(':
            case ')':
                return true;
            default:
                return false;
        }
    }

    private int opPrec(char c) {
        switch (c) {
            case '+':
            case '-':
                return 0;

            case '*':
            case '/':
                return 1;

            default:
                return -1;
        }
    }
}

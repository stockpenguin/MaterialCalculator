package com.stockpenguin.materialcalculator;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/*
The Compiler class is not really a compiler, but just the backend of how the calculator processes everything

It is a singleton class, although would probably still work if we just used static methods
 */

public class Compiler {
    /*
    since Compiler is a singleton, we have a private static instance
     */
    private static Compiler instance;

    /* private constructor as well */
    private Compiler() {}

    /* getInstance() method */
    public static Compiler getInstance() {
        if (instance == null) {
            instance = new Compiler();
        }
        return instance;
    }

    /*
    converts an infix String input and outputs the postfix String

    e.g. "1+2" input = "1 1 +" output

    It uses a stack to successfully create a postfix with 1 traversal

    It throws a SyntaxError as well, only through 1 condition though
     */
    public String infixToPostfix(String infix) throws SyntaxError {
        /* StringBuilder so we can append chars throughout the algorithm */
        StringBuilder postfix = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);

            if (!isOp(c)) {
                try {
                    if (isOp(infix.charAt(i-1))) {
                        postfix.append(' ');
                    }
                } catch (IndexOutOfBoundsException ignored) { }

                postfix.append(c);
                continue;
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(' ');
                    postfix.append(stack.pop());
                }
                stack.pop();
            } else {
                while (!stack.isEmpty() && opPrec(c) <= opPrec(stack.peek())) {
                    postfix.append(' ');
                    postfix.append(stack.pop());
                }
                stack.push(c);
            }

            try {
                if (isOp(infix.charAt(i+1))) {
                    postfix.append(' ');
                }
            } catch (IndexOutOfBoundsException ignored) { }
        }
        while (!stack.isEmpty()) {
            if (stack.peek() == '(') {
                throw new SyntaxError();
            }
            postfix.append(' ');
            postfix.append(stack.pop());
        }
        return postfix.toString();
    }

    public String solve(String infix) throws SyntaxError {
        String postfix = infixToPostfix(infix);

        if (isOp(postfix.charAt(0))) throw new SyntaxError();

        /* create LinkedList of tokens to solve later */
        List<String> tokens = new LinkedList<>();

        for (int i = 0; i < postfix.length(); i++) {
            char c = postfix.charAt(i);
            StringBuilder currentToken = new StringBuilder(c);

            if (isOp(c) || c == ' ') {
                tokens.add(String.valueOf(c));
                continue;
            }

            while ((i+1) < postfix.length()) {
                char temp = postfix.charAt(i);
                if (!isOp(temp) && temp != ' ') {
                    currentToken.append(temp);
                    i++;
                } else {
                    break;
                }
            }

            tokens.add(currentToken.toString());
        }

        Stack<String> stack = new Stack<>();

        for (String token : tokens) {
            char c = token.charAt(0);

            if (!isOp(c) && c != ' ') {
                stack.push(token);
            } else if (isOp(c)) {
                double x = Double.parseDouble(stack.pop());
                double y = Double.parseDouble(stack.pop());
                double result;

                switch (c) {
                    case '+':
                        result = y + x;
                        break;
                    case '-':
                        result = y - x;
                        break;
                    case '*':
                        result = y * x;
                        break;
                    case '/':
                        result = y / x;
                        break;
                    default:
                        throw new SyntaxError();
                }
                stack.push(String.valueOf(result));
            }
        }
        return stack.pop();
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

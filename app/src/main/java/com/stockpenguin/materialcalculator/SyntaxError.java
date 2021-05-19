package com.stockpenguin.materialcalculator;

/* custom SyntaxError exception, completely useless except for the name */
public class SyntaxError extends Exception {
    public static final String MSG = "SYNTAX ERROR";
    public SyntaxError() {
        super();
    }
}

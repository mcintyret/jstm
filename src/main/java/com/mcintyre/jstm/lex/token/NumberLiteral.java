package com.mcintyre.jstm.lex.token;

/**
 * User: tommcintyre
 * Date: 8/6/14
 */
public class NumberLiteral implements Token {

    private final double value;

    public NumberLiteral(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "NUMBER(" + value + ")";
    }
}

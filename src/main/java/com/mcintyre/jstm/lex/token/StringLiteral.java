package com.mcintyre.jstm.lex.token;

/**
 * User: tommcintyre
 * Date: 8/6/14
 */
public class StringLiteral implements Token {

    private final String value;

    public StringLiteral(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "STRING(" + value + ")";
    }
}

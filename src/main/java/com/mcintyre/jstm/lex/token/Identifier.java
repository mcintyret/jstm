package com.mcintyre.jstm.lex.token;

/**
 * User: tommcintyre
 * Date: 8/6/14
 */
public class Identifier implements Token {

    private final String name;

    public Identifier(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "IDENTIFIER(" + name + ")";
    }
}

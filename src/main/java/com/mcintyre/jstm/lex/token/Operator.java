package com.mcintyre.jstm.lex.token;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * User: tommcintyre
 * Date: 8/6/14
 */
public enum Operator implements Token {
    PLUS("+"),
    MINUS("-"),
    MULTIPLY("*"),
    DIVIDE("/"),
    MODULO("%"),

    DOT("."), // Concatenation and method access

    OPEN_BRACE("{"),
    CLOSE_BRACE("}"),
    OPEN_PARENTHESIS("("),
    CLOSE_PARENTHESIS(")"),
    OPEN_SQUARE_BRACKET("["),
    CLOSE_SQUARE_BRACKET("]"),

    AND("&"),
    AND_SS("&&"),
    OR("|"),
    OR_SS("||"),
    XOR("^"),
    NEGATE("!"),

    ASSIGN("="),
    PLUS_EQUALS("+="),
    MINUS_EQUALS("-="),
    MULTIPLY_EQUALS("*="),
    DIVIDE_EQUALS("/="),
    MODULO_EQUALS("%="),

    AND_EQUALS("&="),
    OR_EQUALS("|="),

    INCREMENT("++"),
    DECREMENT("--"),

    EQUALS("=="),
    NOT_EQUALS("!="),
    GREATER_THAN(">"),
    GREATER_THAN_OR_EQUAL(">="),
    LESS_THAN("<"),
    LESS_THAN_OR_EQUAL("<="),

    COMMA(","),
    COLON(":"),
    QUESTION_MARK("?"),
    SEMI_COLON(";"),

    SHIFT_LEFT("<<"),
    SHIFT_RIGHT(">>"),
    UNSIGNED_SHIFT_RIGHT(">>>");

    private static final Map<String, Operator> MAP = createMap();

    private static Map<String, Operator> createMap() {
        Map<String, Operator> map = new HashMap<>();
        for (Operator operator : values()) {
            map.put(operator.value, operator);
        }
        return Collections.unmodifiableMap(map);
    }

    private final String value;

    private Operator(String value) {
        this.value = value;
    }

    public static Operator forString(String str) {
        return MAP.get(str);
    }


}

package com.mcintyre.jstm.lex.token;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * User: tommcintyre
 * Date: 8/6/14
 */
public enum Keyword implements Token {
    VAR,
    FOR,
    DO,
    WHILE,
    IN,
    FUNCTION,
    NULL,
    UNDEFINED,
    IF,
    ELSE,
    THIS,
    SWITCH,
    CASE,
    RETURN,
    DELETE,
    CONTINUE,
    BREAK;

    private static final Map<String, Keyword> MAP = createMap();

    private static Map<String, Keyword> createMap() {
        Map<String, Keyword> map = new HashMap<>();
        for (Keyword keyword : values()) {
            map.put(keyword.name().toLowerCase(), keyword);
        }
        return Collections.unmodifiableMap(map);
    }

    public static Keyword forString(String str) {
        return MAP.get(str);
    }
}

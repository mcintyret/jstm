package com.mcintyre.jstm.interpret.core;

/**
 * User: tommcintyre
 * Date: 8/7/14
 */
public class JsString implements JsType {

    private final String value;

    public JsString(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

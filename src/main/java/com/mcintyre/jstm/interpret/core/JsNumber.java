package com.mcintyre.jstm.interpret.core;

/**
 * User: tommcintyre
 * Date: 8/7/14
 */
public class JsNumber implements JsType {

    private final double value;

    public JsNumber(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}

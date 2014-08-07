package com.mcintyre.jstm.interpret.core;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * User: tommcintyre
 * Date: 8/7/14
 */
public class JsObject implements JsType {

    private final Map<String, JsType> contents = new LinkedHashMap<>();

    public void set(String name, JsType value) {
        contents.put(name, value);
    }

    public JsType get(String name) {
        return contents.get(name);
    }

    public void delete(String name) {
        contents.remove(name);
    }


}

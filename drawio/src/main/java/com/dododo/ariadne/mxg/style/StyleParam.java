package com.dododo.ariadne.mxg.style;

public abstract class StyleParam {

    protected final String key;

    protected final Object value;

    protected StyleParam(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }
}

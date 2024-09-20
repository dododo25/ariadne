package com.dododo.ariadne.mxg.model;

public abstract class MxAbstractCell {

    protected MxAbstractCell() {}

    protected abstract String getId();

    protected static String prepareStyleString(String key, Object value) {
        if (value == null) {
            return key;
        }

        return String.format("%s=%s", key, value);
    }
}

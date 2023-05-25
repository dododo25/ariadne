package com.dododo.ariadne.core.model;

public abstract class SimpleState extends ChainState {

    protected final String value;

    protected SimpleState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        if (value == null) {
            return String.format("%s(value=null)", getClass().getSimpleName());
        }

        return String.format("%s(value='%s')", getClass().getSimpleName(), value);
    }
}

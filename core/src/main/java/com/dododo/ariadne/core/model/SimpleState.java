package com.dododo.ariadne.core.model;

public abstract class SimpleState extends ChainState {

    protected final String value;

    protected SimpleState() {
        this(null);
    }

    protected SimpleState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public int compareTo(State o) {
        return compareBySingleValue(o, s -> ((SimpleState) s).getValue());
    }

    @Override
    public String toString() {
        if (value == null) {
            return String.format("%s(value=null)", getClass().getSimpleName());
        }

        return String.format("%s(value='%s')", getClass().getSimpleName(), value);
    }
}

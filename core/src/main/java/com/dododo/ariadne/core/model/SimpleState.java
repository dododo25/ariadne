package com.dododo.ariadne.core.model;

import java.util.Objects;

public abstract class SimpleState extends ChainState {

    protected final String value;

    protected SimpleState(String value) {
        this.value = Objects.requireNonNull(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("%s(value='%s')", getClass().getSimpleName(), value);
    }
}

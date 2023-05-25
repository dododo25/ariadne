package com.dododo.ariadne.drawio.model;

public abstract class SimpleBlock extends ChainBlock {

    protected final String value;

    protected SimpleBlock(int id, String value) {
        super(id);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("%s(id=%d, x=%d, y=%d, value='%s')", getClass().getSimpleName(), id, x, y, value);
    }
}

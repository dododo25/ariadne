package com.dododo.ariadne.core.model;

import com.dododo.ariadne.core.contract.FlowchartContract;

import java.util.Objects;

public final class Statement extends SimpleState {

    public Statement(String value) {
        super(value);
    }

    @Override
    public void accept(FlowchartContract contract) {
        contract.accept(this);
    }

    @Override
    public int compareTo(State o) {
        return o instanceof Statement && Objects.equals(((Statement) o).value, this.value) ? 0 : 1;
    }
}

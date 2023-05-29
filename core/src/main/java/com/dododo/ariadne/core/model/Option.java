package com.dododo.ariadne.core.model;

import com.dododo.ariadne.core.contract.FlowchartContract;

import java.util.Objects;

public class Option extends SimpleState {

    public Option(String value) {
        super(value);
    }

    @Override
    public void accept(FlowchartContract contract) {
        contract.accept(this);
    }

    @Override
    public int compareTo(State o) {
        return o instanceof Option && Objects.equals(value, ((Option) o).value) ? 0 : 1;
    }
}

package com.dododo.ariadne.core.model;

import com.dododo.ariadne.core.contract.FlowchartContract;

import java.util.Objects;

public final class Text extends SimpleState {

    public Text(String value) {
        super(value);
    }

    @Override
    public void accept(FlowchartContract contract) {
        contract.accept(this);
    }

    @Override
    public int compareTo(State o) {
        return o instanceof Text && Objects.equals(((Text) o).value, this.value) ? 0 : 1;
    }
}

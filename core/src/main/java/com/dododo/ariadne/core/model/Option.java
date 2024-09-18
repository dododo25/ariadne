package com.dododo.ariadne.core.model;

import com.dododo.ariadne.core.contract.FlowchartContract;

public class Option extends SimpleState {

    public Option(String value) {
        super(value);
    }

    @Override
    public void accept(FlowchartContract contract) {
        contract.accept(this);
    }
}

package com.dododo.ariadne.core.model;

import com.dododo.ariadne.core.contract.FlowchartContract;

public final class Text extends SimpleState {

    public Text(String value) {
        super(value);
    }

    @Override
    public void accept(FlowchartContract contract) {
        contract.accept(this);
    }
}

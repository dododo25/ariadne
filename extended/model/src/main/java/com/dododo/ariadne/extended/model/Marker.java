package com.dododo.ariadne.extended.model;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.SimpleState;
import com.dododo.ariadne.extended.contract.ExtendedFlowchartContract;

public final class Marker extends SimpleState {

    public Marker(String value) {
        super(value);
    }

    @Override
    public void accept(FlowchartContract contract) {
        ((ExtendedFlowchartContract) contract).accept(this);
    }
}

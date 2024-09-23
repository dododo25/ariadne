package com.dododo.ariadne.renpy.model;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.SimpleState;
import com.dododo.ariadne.renpy.contract.RenPyFlowchartContract;

public final class CallToState extends SimpleState {

    public CallToState(String value) {
        super(value);
    }

    @Override
    public void accept(FlowchartContract contract) {
        ((RenPyFlowchartContract) contract).accept(this);
    }
}

package com.dododo.ariadne.core.model;

import com.dododo.ariadne.core.contract.FlowchartContract;

public class CycleEntryState extends SimpleState {

    public CycleEntryState(String value) {
        super(value);
    }

    @Override
    public void accept(FlowchartContract contract) {
        contract.accept(this);
    }
}
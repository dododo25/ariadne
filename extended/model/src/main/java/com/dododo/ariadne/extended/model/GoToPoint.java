package com.dododo.ariadne.extended.model;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.extended.contract.ExtendedFlowchartContract;

public final class GoToPoint extends State {

    private final String value;

    public GoToPoint(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public void accept(FlowchartContract contract) {
        ((ExtendedFlowchartContract) contract).accept(this);
    }

    @Override
    public int compareTo(State o) {
        return compareBySingleValue(o, state -> ((GoToPoint) state).getValue());
    }

    @Override
    public String toString() {
        return String.format("%s(value='%s')", getClass().getSimpleName(), value);
    }
}

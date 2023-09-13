package com.dododo.ariadne.renpy.common.model;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.renpy.common.contract.RenPyFlowchartContract;

public class JumpToPoint extends State {

    private final String value;

    public JumpToPoint(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public void accept(FlowchartContract contract) {
        ((RenPyFlowchartContract) contract).accept(this);
    }

    @Override
    public int compareTo(State o) {
        return compareBySingleValue(o, state -> ((JumpToPoint) state).getValue());
    }

    @Override
    public String toString() {
        return String.format("%s(value='%s')", getClass().getSimpleName(), value);
    }
}

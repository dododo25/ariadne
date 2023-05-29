package com.dododo.ariadne.renpy.common.model;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.renpy.common.contract.RenPyFlowchartContract;

import java.util.Objects;

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
        return o instanceof JumpToPoint && Objects.equals(value, ((JumpToPoint) o).value) ? 0 : 1;
    }

    @Override
    public String toString() {
        return String.format("%s(value='%s')", getClass().getSimpleName(), value);
    }
}

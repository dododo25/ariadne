package com.dododo.ariadne.xml.common.model;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.xml.common.contract.XmlFlowchartContract;

import java.util.Objects;

public final class GoToPoint extends State {

    private final String value;

    public GoToPoint(String value) {
        this.value = Objects.requireNonNull(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public void accept(FlowchartContract contract) {
        ((XmlFlowchartContract) contract).accept(this);
    }

    @Override
    public int compareTo(State o) {
        return o instanceof GoToPoint && Objects.equals(((GoToPoint) o).value, value) ? 0 : 1;
    }

    @Override
    public String toString() {
        return String.format("%s(value='%s')", getClass().getSimpleName(), value);
    }
}

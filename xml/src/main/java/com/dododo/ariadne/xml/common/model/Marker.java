package com.dododo.ariadne.xml.common.model;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.SimpleState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.xml.common.contract.XmlFlowchartContract;

import java.util.Objects;

public final class Marker extends SimpleState {

    public Marker(String value) {
        super(value);
    }

    @Override
    public void accept(FlowchartContract contract) {
        ((XmlFlowchartContract) contract).accept(this);
    }

    @Override
    public int compareTo(State o) {
        return o instanceof Marker && Objects.equals(value, ((Marker) o).value) ? 0 : 1;
    }
}

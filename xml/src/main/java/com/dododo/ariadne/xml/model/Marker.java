package com.dododo.ariadne.xml.model;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.SimpleState;
import com.dododo.ariadne.xml.contract.XmlFlowchartContract;

public final class Marker extends SimpleState {

    public Marker(String value) {
        super(value);
    }

    @Override
    public void accept(FlowchartContract contract) {
        ((XmlFlowchartContract) contract).accept(this);
    }
}

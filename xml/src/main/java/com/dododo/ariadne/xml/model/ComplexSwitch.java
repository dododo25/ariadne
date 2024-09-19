package com.dododo.ariadne.xml.model;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.xml.contract.XmlFlowchartContract;

public final class ComplexSwitch extends ComplexState {

    public ComplexSwitch() {
        super();
    }

    @Override
    public void accept(FlowchartContract contract) {
        ((XmlFlowchartContract) contract).accept(this);
    }
}

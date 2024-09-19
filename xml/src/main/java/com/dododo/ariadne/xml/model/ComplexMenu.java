package com.dododo.ariadne.xml.model;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.xml.contract.XmlFlowchartContract;

public final class ComplexMenu extends ComplexState {

    public ComplexMenu() {
        super();
    }

    @Override
    public void accept(FlowchartContract contract) {
        ((XmlFlowchartContract) contract).accept(this);
    }
}

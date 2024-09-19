package com.dododo.ariadne.xml.model;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.xml.contract.XmlFlowchartContract;

public final class ComplexSwitchBranch extends ComplexState {

    private final String value;

    public ComplexSwitchBranch() {
        this(null);
    }

    public ComplexSwitchBranch(String value) {
        super();
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public void accept(FlowchartContract contract) {
        ((XmlFlowchartContract) contract).accept(this);
    }
}

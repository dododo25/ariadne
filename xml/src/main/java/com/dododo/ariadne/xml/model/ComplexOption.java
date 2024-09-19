package com.dododo.ariadne.xml.model;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.xml.contract.XmlFlowchartContract;

public final class ComplexOption extends ComplexState {

    private final String value;

    private final String condition;

    public ComplexOption(String value, String condition) {
        super();

        this.value = value;
        this.condition = condition;
    }

    public String getValue() {
        return value;
    }

    public String getCondition() {
        return condition;
    }

    @Override
    public void accept(FlowchartContract contract) {
        ((XmlFlowchartContract) contract).accept(this);
    }
}

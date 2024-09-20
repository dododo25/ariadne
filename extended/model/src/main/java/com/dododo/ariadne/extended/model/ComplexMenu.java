package com.dododo.ariadne.extended.model;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.extended.contract.ExtendedFlowchartContract;

public final class ComplexMenu extends ComplexState {

    public ComplexMenu() {
        super();
    }

    @Override
    public void accept(FlowchartContract contract) {
        ((ExtendedFlowchartContract) contract).accept(this);
    }
}

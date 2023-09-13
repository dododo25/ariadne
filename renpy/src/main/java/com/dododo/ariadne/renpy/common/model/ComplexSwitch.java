package com.dododo.ariadne.renpy.common.model;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.renpy.common.contract.RenPyFlowchartContract;

public class ComplexSwitch extends ComplexState {

    public ComplexSwitch() {
        super();
    }

    @Override
    public void accept(FlowchartContract contract) {
        ((RenPyFlowchartContract) contract).accept(this);
    }
}

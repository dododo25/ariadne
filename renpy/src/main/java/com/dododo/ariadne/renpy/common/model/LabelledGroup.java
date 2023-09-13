package com.dododo.ariadne.renpy.common.model;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.SimpleState;
import com.dododo.ariadne.renpy.common.contract.RenPyFlowchartContract;

public class LabelledGroup extends SimpleState {

    public LabelledGroup(String value) {
        super(value);
    }

    @Override
    public void accept(FlowchartContract contract) {
        ((RenPyFlowchartContract) contract).accept(this);
    }
}

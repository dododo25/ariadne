package com.dododo.ariadne.renpy.model;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.extended.model.ComplexState;
import com.dododo.ariadne.renpy.contract.RenPyFlowchartContract;

public class SkipComplexState extends ComplexState {

    @Override
    public void accept(FlowchartContract contract) {
        ((RenPyFlowchartContract) contract).accept(this);
    }
}
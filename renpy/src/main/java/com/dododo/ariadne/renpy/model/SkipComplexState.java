package com.dododo.ariadne.renpy.model;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.extended.model.ComplexState;

public class SkipComplexState extends ComplexState {

    @Override
    public void accept(FlowchartContract contract) {
        // no use in mouse actions
    }
}
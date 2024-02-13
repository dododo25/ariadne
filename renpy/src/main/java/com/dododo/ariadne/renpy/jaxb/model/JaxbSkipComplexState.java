package com.dododo.ariadne.renpy.jaxb.model;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.jaxb.model.JaxbComplexState;

public class JaxbSkipComplexState extends JaxbComplexState {

    @Override
    public void accept(JaxbFlowchartContract contract) {
        // not used in mouse actions
    }
}

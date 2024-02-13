package com.dododo.ariadne.renpy.jaxb.model;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.jaxb.model.JaxbComplexState;
import com.dododo.ariadne.renpy.jaxb.contract.RenPyJaxbFlowchartContract;

public class JaxbInitGroupState extends JaxbComplexState {

    @Override
    public void accept(JaxbFlowchartContract contract) {
        ((RenPyJaxbFlowchartContract) contract).accept(this);
    }
}

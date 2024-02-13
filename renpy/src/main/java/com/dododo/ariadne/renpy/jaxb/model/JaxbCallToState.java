package com.dododo.ariadne.renpy.jaxb.model;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.jaxb.model.JaxbSimpleState;
import com.dododo.ariadne.renpy.jaxb.contract.RenPyJaxbFlowchartContract;

public class JaxbCallToState extends JaxbSimpleState {

    public JaxbCallToState(String value) {
        super(value);
    }

    @Override
    public void accept(JaxbFlowchartContract contract) {
        ((RenPyJaxbFlowchartContract) contract).accept(this);
    }
}

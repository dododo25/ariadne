package com.dododo.ariadne.xml.jaxb.model;

import com.dododo.ariadne.xml.jaxb.contract.JaxbFlowchartContract;

public class JaxbPassState implements JaxbState {

    @Override
    public void accept(JaxbFlowchartContract contract) {
        contract.accept(this);
    }
}

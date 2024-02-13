package com.dododo.ariadne.renpy.jaxb.model;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.jaxb.model.JaxbSimpleState;

public class JaxbSingleLineComment extends JaxbSimpleState {

    public JaxbSingleLineComment() {
        super(null);
    }

    @Override
    public void accept(JaxbFlowchartContract contract) {
        // not used in mouse actions
    }
}

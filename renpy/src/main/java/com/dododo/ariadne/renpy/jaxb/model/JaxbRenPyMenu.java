package com.dododo.ariadne.renpy.jaxb.model;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.jaxb.model.JaxbComposedState;
import com.dododo.ariadne.renpy.jaxb.contract.RenPyJaxbFlowchartContract;

public class JaxbRenPyMenu extends JaxbComposedState {

    public JaxbRenPyMenu() {
        this(null);
    }

    public JaxbRenPyMenu(String value) {
        super(value);
    }

    @Override
    public void accept(JaxbFlowchartContract contract) {
        ((RenPyJaxbFlowchartContract) contract).accept(this);
    }
}

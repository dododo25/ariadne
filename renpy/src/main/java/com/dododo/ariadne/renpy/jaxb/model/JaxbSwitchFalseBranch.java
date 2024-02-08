package com.dododo.ariadne.renpy.jaxb.model;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.jaxb.model.JaxbSwitchBranch;
import com.dododo.ariadne.renpy.jaxb.contract.RenPyJaxbFlowchartContract;

public class JaxbSwitchFalseBranch extends JaxbSwitchBranch {

    public JaxbSwitchFalseBranch() {
        this(null);
    }

    public JaxbSwitchFalseBranch(String value) {
        super(value);
    }

    @Override
    public void accept(JaxbFlowchartContract contract) {
        ((RenPyJaxbFlowchartContract) contract).accept(this);
    }
}

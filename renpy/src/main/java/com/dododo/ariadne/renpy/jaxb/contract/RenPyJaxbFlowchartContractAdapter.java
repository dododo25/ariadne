package com.dododo.ariadne.renpy.jaxb.contract;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContractAdapter;
import com.dododo.ariadne.renpy.jaxb.model.JaxbCallToState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbInitGroupState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbLabelledGroup;
import com.dododo.ariadne.renpy.jaxb.model.JaxbMenu;
import com.dododo.ariadne.renpy.jaxb.model.JaxbSwitchFalseBranch;

public abstract class RenPyJaxbFlowchartContractAdapter extends JaxbFlowchartContractAdapter
        implements RenPyJaxbFlowchartContract {

    @Override
    public void accept(JaxbInitGroupState state) {}

    @Override
    public void accept(JaxbMenu menu) {}

    @Override
    public void accept(JaxbSwitchFalseBranch switchBranch) {}

    @Override
    public void accept(JaxbLabelledGroup group) {}

    @Override
    public void accept(JaxbCallToState state) {}
}

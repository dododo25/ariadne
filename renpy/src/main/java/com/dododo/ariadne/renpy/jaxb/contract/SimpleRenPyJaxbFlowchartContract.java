package com.dododo.ariadne.renpy.jaxb.contract;

import com.dododo.ariadne.jaxb.contract.SimpleJaxbFlowchartContract;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbCallToState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbInitGroupState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbLabelledGroup;
import com.dododo.ariadne.renpy.jaxb.model.JaxbRenPyMenu;
import com.dododo.ariadne.renpy.jaxb.model.JaxbSwitchFalseBranch;

public abstract class SimpleRenPyJaxbFlowchartContract extends SimpleJaxbFlowchartContract
        implements RenPyJaxbFlowchartContract {

    @Override
    public final void accept(JaxbInitGroupState state) {
        acceptState(state);
    }

    @Override
    public final void accept(JaxbRenPyMenu menu) {
        acceptState(menu);
    }

    @Override
    public final void accept(JaxbSwitchFalseBranch switchBranch) {
        acceptState(switchBranch);
    }

    @Override
    public final void accept(JaxbLabelledGroup group) {
        acceptState(group);
    }

    @Override
    public final void accept(JaxbCallToState state) {
        acceptState(state);
    }

    public abstract void acceptState(JaxbState state);
}

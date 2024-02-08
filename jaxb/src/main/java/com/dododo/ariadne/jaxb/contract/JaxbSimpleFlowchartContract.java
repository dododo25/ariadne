package com.dododo.ariadne.jaxb.contract;

import com.dododo.ariadne.jaxb.model.JaxbComplexSwitch;
import com.dododo.ariadne.jaxb.model.JaxbEndState;
import com.dododo.ariadne.jaxb.model.JaxbGoToState;
import com.dododo.ariadne.jaxb.model.JaxbLabel;
import com.dododo.ariadne.jaxb.model.JaxbMenu;
import com.dododo.ariadne.jaxb.model.JaxbOption;
import com.dododo.ariadne.jaxb.model.JaxbPassState;
import com.dododo.ariadne.jaxb.model.JaxbReply;
import com.dododo.ariadne.jaxb.model.JaxbRootState;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.jaxb.model.JaxbSwitchBranch;
import com.dododo.ariadne.jaxb.model.JaxbText;

public abstract class JaxbSimpleFlowchartContract implements JaxbFlowchartContract {

    @Override
    public final void accept(JaxbRootState state) {
        acceptState(state);
    }

    @Override
    public final void accept(JaxbText text) {
        acceptState(text);
    }

    @Override
    public final void accept(JaxbReply reply) {
        acceptState(reply);
    }

    @Override
    public final void accept(JaxbMenu menu) {
        acceptState(menu);
    }

    @Override
    public final void accept(JaxbOption option) {
        acceptState(option);
    }

    @Override
    public final void accept(JaxbComplexSwitch complexSwitch) {
        acceptState(complexSwitch);
    }

    @Override
    public final void accept(JaxbSwitchBranch switchBranch) {
        acceptState(switchBranch);
    }

    @Override
    public final void accept(JaxbLabel label) {
        acceptState(label);
    }

    @Override
    public final void accept(JaxbGoToState state) {
        acceptState(state);
    }

    @Override
    public final void accept(JaxbPassState state) {
        acceptState(state);
    }

    @Override
    public final void accept(JaxbEndState state) {
        acceptState(state);
    }

    public abstract void acceptState(JaxbState state);
}

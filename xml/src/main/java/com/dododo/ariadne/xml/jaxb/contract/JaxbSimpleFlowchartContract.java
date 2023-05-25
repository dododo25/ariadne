package com.dododo.ariadne.xml.jaxb.contract;

import com.dododo.ariadne.xml.jaxb.model.JaxbComplexSwitch;
import com.dododo.ariadne.xml.jaxb.model.JaxbEndState;
import com.dododo.ariadne.xml.jaxb.model.JaxbGoToState;
import com.dododo.ariadne.xml.jaxb.model.JaxbMarker;
import com.dododo.ariadne.xml.jaxb.model.JaxbPassState;
import com.dododo.ariadne.xml.jaxb.model.JaxbRootState;
import com.dododo.ariadne.xml.jaxb.model.JaxbState;
import com.dododo.ariadne.xml.jaxb.model.JaxbStatement;
import com.dododo.ariadne.xml.jaxb.model.JaxbSwitchBranch;

public abstract class JaxbSimpleFlowchartContract implements JaxbFlowchartContract {

    @Override
    public final void accept(JaxbRootState state) {
        acceptState(state);
    }

    @Override
    public final void accept(JaxbStatement statement) {
        acceptState(statement);
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
    public final void accept(JaxbMarker marker) {
        acceptState(marker);
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

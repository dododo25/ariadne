package com.dododo.ariadne.xml.jaxb.mouse;

import com.dododo.ariadne.xml.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.xml.jaxb.model.JaxbComplexSwitch;
import com.dododo.ariadne.xml.jaxb.model.JaxbEndState;
import com.dododo.ariadne.xml.jaxb.model.JaxbGoToState;
import com.dododo.ariadne.xml.jaxb.model.JaxbMarker;
import com.dododo.ariadne.xml.jaxb.model.JaxbPassState;
import com.dododo.ariadne.xml.jaxb.model.JaxbRootState;
import com.dododo.ariadne.xml.jaxb.model.JaxbText;
import com.dododo.ariadne.xml.jaxb.model.JaxbSwitchBranch;
import com.dododo.ariadne.xml.jaxb.mouse.strategy.JaxbFlowchartMouseStrategy;

public class JaxbFlowchartMouse implements JaxbFlowchartContract {

    private final JaxbFlowchartContract callback;

    private final JaxbFlowchartMouseStrategy strategy;

    public JaxbFlowchartMouse(JaxbFlowchartContract callback, JaxbFlowchartMouseStrategy strategy) {
        this.callback = callback;
        this.strategy = strategy;
    }

    @Override
    public void accept(JaxbRootState state) {
        strategy.acceptComplexState(state, callback, this);
    }

    @Override
    public void accept(JaxbComplexSwitch complexSwitch) {
        strategy.acceptComplexState(complexSwitch, callback, this);
    }

    @Override
    public void accept(JaxbSwitchBranch switchBranch) {
        strategy.acceptComplexState(switchBranch, callback, this);
    }

    @Override
    public void accept(JaxbText text) {
        text.accept(callback);
    }

    @Override
    public void accept(JaxbMarker marker) {
        marker.accept(callback);
    }

    @Override
    public void accept(JaxbGoToState state) {
        state.accept(callback);
    }

    @Override
    public void accept(JaxbPassState state) {
        state.accept(callback);
    }

    @Override
    public void accept(JaxbEndState state) {
        state.accept(callback);
    }
}

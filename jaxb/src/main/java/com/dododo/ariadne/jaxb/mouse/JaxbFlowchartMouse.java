package com.dododo.ariadne.jaxb.mouse;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.jaxb.model.JaxbComplexSwitch;
import com.dododo.ariadne.jaxb.model.JaxbEndState;
import com.dododo.ariadne.jaxb.model.JaxbGoToState;
import com.dododo.ariadne.jaxb.model.JaxbMarker;
import com.dododo.ariadne.jaxb.model.JaxbMenu;
import com.dododo.ariadne.jaxb.model.JaxbOption;
import com.dododo.ariadne.jaxb.model.JaxbPassState;
import com.dododo.ariadne.jaxb.model.JaxbReply;
import com.dododo.ariadne.jaxb.model.JaxbRootState;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.jaxb.model.JaxbSwitchBranch;
import com.dododo.ariadne.jaxb.model.JaxbText;
import com.dododo.ariadne.jaxb.mouse.strategy.JaxbFlowchartMouseStrategy;

import java.util.HashSet;
import java.util.Set;

public class JaxbFlowchartMouse implements JaxbFlowchartContract {

    private final JaxbFlowchartContract callback;

    private final JaxbFlowchartMouseStrategy strategy;

    private final Set<JaxbState> visited;

    public JaxbFlowchartMouse(JaxbFlowchartContract callback, JaxbFlowchartMouseStrategy strategy) {
        this.callback = callback;
        this.strategy = strategy;
        this.visited = new HashSet<>();
    }

    @Override
    public void accept(JaxbRootState state) {
        strategy.acceptComplexState(state, callback, this, visited);
    }

    @Override
    public void accept(JaxbMenu menu) {
        strategy.acceptComplexState(menu, callback, this, visited);
    }

    @Override
    public void accept(JaxbOption option) {
        strategy.acceptComplexState(option, callback, this, visited);
    }

    @Override
    public void accept(JaxbComplexSwitch complexSwitch) {
        strategy.acceptComplexState(complexSwitch, callback, this, visited);
    }

    @Override
    public void accept(JaxbSwitchBranch switchBranch) {
        strategy.acceptComplexState(switchBranch, callback, this, visited);
    }

    @Override
    public void accept(JaxbText text) {
        text.accept(callback);
    }

    @Override
    public void accept(JaxbReply reply) {
        reply.accept(callback);
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

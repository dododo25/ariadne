package com.dododo.ariadne.renpy.jaxb.mouse;

import com.dododo.ariadne.renpy.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.renpy.jaxb.model.JaxbCallToState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbComplexSwitch;
import com.dododo.ariadne.renpy.jaxb.model.JaxbEndState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbInitGroupState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbJumpToState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbMenu;
import com.dododo.ariadne.renpy.jaxb.model.JaxbOption;
import com.dododo.ariadne.renpy.jaxb.model.JaxbPassState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbReply;
import com.dododo.ariadne.renpy.jaxb.model.JaxbGroupState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbText;
import com.dododo.ariadne.renpy.jaxb.model.JaxbLabelledGroup;
import com.dododo.ariadne.renpy.jaxb.model.JaxbSwitchBranch;
import com.dododo.ariadne.renpy.jaxb.model.JaxbSwitchFalseBranch;
import com.dododo.ariadne.renpy.jaxb.mouse.strategy.JaxbFlowchartMouseStrategy;

public class JaxbFlowchartMouse implements JaxbFlowchartContract {

    private final JaxbFlowchartContract callback;

    private final JaxbFlowchartMouseStrategy strategy;

    public JaxbFlowchartMouse(JaxbFlowchartContract callback, JaxbFlowchartMouseStrategy strategy) {
        this.callback = callback;
        this.strategy = strategy;
    }

    @Override
    public void accept(JaxbGroupState state) {
        strategy.acceptComplexState(state, callback, this);
    }

    @Override
    public void accept(JaxbInitGroupState state) {
        strategy.acceptComplexState(state, callback, this);
    }

    @Override
    public void accept(JaxbMenu menu) {
        strategy.acceptComplexState(menu, callback, this);
    }

    @Override
    public void accept(JaxbOption option) {
        strategy.acceptComplexState(option, callback, this);
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
    public void accept(JaxbSwitchFalseBranch switchBranch) {
        strategy.acceptComplexState(switchBranch, callback, this);
    }

    @Override
    public void accept(JaxbLabelledGroup group) {
        strategy.acceptComplexState(group, callback, this);
    }

    @Override
    public void accept(JaxbText text) {
        callback.accept(text);
    }

    @Override
    public void accept(JaxbReply reply) {
        callback.accept(reply);
    }

    @Override
    public void accept(JaxbJumpToState state) {
        callback.accept(state);
    }

    @Override
    public void accept(JaxbCallToState state) {
        callback.accept(state);
    }

    @Override
    public void accept(JaxbPassState state) {
        callback.accept(state);
    }

    @Override
    public void accept(JaxbEndState state) {
        callback.accept(state);
    }
}

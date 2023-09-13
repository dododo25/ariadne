package com.dododo.ariadne.renpy.jaxb.contract;

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
import com.dododo.ariadne.renpy.jaxb.model.JaxbState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbText;
import com.dododo.ariadne.renpy.jaxb.model.JaxbLabelledGroup;
import com.dododo.ariadne.renpy.jaxb.model.JaxbSwitchBranch;
import com.dododo.ariadne.renpy.jaxb.model.JaxbSwitchFalseBranch;

public abstract class JaxbSimpleFlowchartContract implements JaxbFlowchartContract {

    @Override
    public final void accept(JaxbGroupState state) {
        acceptState(state);
    }

    @Override
    public final void accept(JaxbInitGroupState state) {
        acceptState(state);
    }

    @Override
    public void accept(JaxbText text) {
        acceptState(text);
    }

    @Override
    public void accept(JaxbReply reply) {
        acceptState(reply);
    }

    @Override
    public void accept(JaxbMenu menu) {
        acceptState(menu);
    }

    @Override
    public void accept(JaxbOption option) {
        acceptState(option);
    }

    @Override
    public void accept(JaxbComplexSwitch complexSwitch) {
        acceptState(complexSwitch);
    }

    @Override
    public void accept(JaxbSwitchBranch switchBranch) {
        acceptState(switchBranch);
    }

    @Override
    public void accept(JaxbSwitchFalseBranch switchBranch) {
        acceptState(switchBranch);
    }

    @Override
    public void accept(JaxbLabelledGroup group) {
        acceptState(group);
    }

    @Override
    public void accept(JaxbJumpToState state) {
        acceptState(state);
    }

    @Override
    public void accept(JaxbCallToState state) {
        acceptState(state);
    }

    @Override
    public void accept(JaxbPassState state) {
        acceptState(state);
    }

    @Override
    public void accept(JaxbEndState state) {
        acceptState(state);
    }

    public abstract void acceptState(JaxbState state);
}

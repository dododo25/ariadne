package com.dododo.ariadne.renpy.jaxb.contract;

import com.dododo.ariadne.renpy.jaxb.model.JaxbCallToState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbComplexSwitch;
import com.dododo.ariadne.renpy.jaxb.model.JaxbEndState;
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

public abstract class JaxbFlowchartContractAdapter implements JaxbFlowchartContract {

    @Override
    public void accept(JaxbGroupState state) {}

    @Override
    public void accept(JaxbText text) {}

    @Override
    public void accept(JaxbReply reply) {}

    @Override
    public void accept(JaxbMenu menu) {}

    @Override
    public void accept(JaxbOption option) {}

    @Override
    public void accept(JaxbComplexSwitch complexSwitch) {}

    @Override
    public void accept(JaxbSwitchBranch switchBranch) {}

    @Override
    public void accept(JaxbSwitchFalseBranch switchBranch) {}

    @Override
    public void accept(JaxbLabelledGroup group) {}

    @Override
    public void accept(JaxbJumpToState state) {}

    @Override
    public void accept(JaxbCallToState state) {}

    @Override
    public void accept(JaxbPassState state) {}

    @Override
    public void accept(JaxbEndState state) {}
}

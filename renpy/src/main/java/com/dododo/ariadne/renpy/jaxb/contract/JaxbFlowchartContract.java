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

public interface JaxbFlowchartContract {

    void accept(JaxbGroupState state);

    void accept(JaxbText text);

    void accept(JaxbReply reply);

    void accept(JaxbMenu menu);

    void accept(JaxbOption option);

    void accept(JaxbComplexSwitch complexSwitch);

    void accept(JaxbSwitchBranch switchBranch);

    void accept(JaxbSwitchFalseBranch switchBranch);

    void accept(JaxbLabelledGroup group);

    void accept(JaxbJumpToState state);

    void accept(JaxbCallToState state);

    void accept(JaxbPassState state);

    void accept(JaxbEndState state);

}
